package com.example.app.domain.genre;

import com.example.app.domain.genre.genre.GenreDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Optional<GenreDto> findGenreByName(String name) {
        return genreRepository.findByNameIgnoreCase(name)
                .map(GenreMapper::map);
    }

    public List<GenreDto> findAllGenres() {
        return StreamSupport.stream(genreRepository.findAll().spliterator(), false)
                .map(GenreMapper::map)
                .sorted(Comparator.comparing(GenreDto::getName))
                .toList();
    }

    @Transactional
    public void addGenre(GenreDto genre) {
        Genre genreToSave = new Genre();
        prepareGenreToSave(genreToSave, genre);
        genreRepository.save(genreToSave);
    }

    @Transactional
    public void saveEditedGenre(GenreDto genre) {
        Genre genreById = genreRepository.findById(genre.getId()).orElseThrow();
        prepareGenreToSave(genreById, genre);
    }

    @Transactional
    public GenreDto deleteGenreById(Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        GenreDto genreToDelete = GenreMapper.map(genre);

        genre.getMovies().forEach(m -> m.setGenre(null));

        genreRepository.deleteById(genreToDelete.getId());
        return genreToDelete;
    }

    private void prepareGenreToSave(Genre genreToSet, GenreDto genreToGet) {
        genreToSet.setName(genreToGet.getName());
        genreToSet.setDescription(genreToGet.getDescription());
    }
}
