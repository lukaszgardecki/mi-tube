package com.example.app.domain.movie;

import com.example.app.domain.movie.dto.MovieDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
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
}
