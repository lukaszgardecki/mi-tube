package com.example.app.domain.movie;

import com.example.app.domain.genre.Genre;
import com.example.app.domain.genre.GenreRepository;
import com.example.app.domain.movie.dto.MovieDto;
import com.example.app.domain.movie.dto.MovieSaveDto;
import com.example.app.storage.FileStorageService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final FileStorageService fileStorageService;

    public MovieService(MovieRepository movieRepository,
                        GenreRepository genreRepository,
                        FileStorageService fileStorageService) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.fileStorageService = fileStorageService;
    }

    public List<MovieDto> findAllMovies() {
        return StreamSupport.stream(movieRepository.findAll().spliterator(), false)
                .map(MovieMapper::map)
                .toList();
    }

    public List<MovieDto> findAllPromotedMovies() {
        return movieRepository.findAllByPromotedIsTrue().stream()
                .map(MovieMapper::map)
                .toList();
    }

    public Optional<MovieDto> findMovieById(Long id) {
        return movieRepository.findById(id).map(MovieMapper::map);
    }

    public List<MovieDto> findMoviesByGenreName(String genre) {
        return movieRepository.findAllByGenre_NameIgnoreCase(genre).stream()
                .map(MovieMapper::map)
                .toList();
    }

    public void addMovie(MovieSaveDto movieToSave) {
        Movie movie = new Movie();
        prepareObjectToSave(movie, movieToSave);
        String genre = movieToSave.getGenre();
        Genre genre1 = genreRepository.findByNameIgnoreCase(genre).orElseThrow();
        genre1.getMovies().add(movie);
        movieRepository.save(movie);
    }

    @Transactional
    public void saveEditedMovie(MovieSaveDto movieToSave) {
        Movie movieById = movieRepository.findById(movieToSave.getId()).orElseThrow();
        prepareObjectToSave(movieById, movieToSave);
    }

    @Transactional
    public MovieDto deleteMovieById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        MovieDto movieToDelete = MovieMapper.map(movie);
        Genre genre = movie.getGenre();
        genre.getMovies().remove(movie);
        movieRepository.deleteById(movieToDelete.getId());
        fileStorageService.deleteImage(movie.getPoster());
        return movieToDelete;
    }

    private void prepareObjectToSave(Movie movieToSet, MovieSaveDto movieToGet) {
        boolean posterIsChanged = !Objects.equals(movieToGet.getPoster().getOriginalFilename(), "")
                && movieToGet.getPoster() != null;
        movieToSet.setTitle(movieToGet.getTitle());
        movieToSet.setOriginalTitle(movieToGet.getOriginalTitle());
        movieToSet.setPromoted(movieToGet.isPromoted());
        movieToSet.setReleaseYear(movieToGet.getReleaseYear());
        movieToSet.setShortDescription(movieToGet.getShortDescription());
        movieToSet.setDescription(movieToGet.getDescription());
        movieToSet.setYoutubeTrailerId(movieToGet.getYoutubeTrailerId());

        Genre genre = genreRepository.findByNameIgnoreCase(movieToGet.getGenre()).orElseThrow();
        movieToSet.setGenre(genre);

        if (posterIsChanged) {
            String savedFileName = fileStorageService.saveImage(movieToGet.getPoster());
            movieToSet.setPoster(savedFileName);
        }
    }

    public List<MovieDto> findTopMovies(int size) {
        Pageable page = Pageable.ofSize(size);
        return movieRepository.findTopByRating(page).stream().map(MovieMapper::map).toList();
    }
}
