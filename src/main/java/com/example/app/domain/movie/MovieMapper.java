package com.example.app.domain.movie;

import com.example.app.domain.movie.dto.MovieDto;

public class MovieMapper {

    static MovieDto map(Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getOriginTitle(),
                movie.getReleaseYear(),
                movie.getGenre().getName(),
                movie.isPromoted()
        );
    }
}
