package com.example.app.domain.genre;

import com.example.app.domain.genre.genre.GenreDto;
import org.springframework.stereotype.Service;

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
                .toList();
    }
}
