package com.example.app.domain.movie;

import com.example.app.domain.movie.dto.MovieDto;
import com.example.app.domain.rating.Rating;

public class MovieMapper {

    static MovieDto map(Movie movie) {

        double avgRating = movie.getRatings().stream()
                .map(Rating::getRating)
                .mapToDouble(val -> val)
                .average().orElse(0);
        int ratingCount = movie.getRatings().size();
        String genre = movie.getGenre() == null ? "" : movie.getGenre().getName();

        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getOriginalTitle(),
                movie.getReleaseYear(),
                movie.getShortDescription(),
                movie.getDescription(),
                movie.getYoutubeTrailerId(),
                genre,
                movie.isPromoted(),
                movie.getPoster(),
                avgRating,
                ratingCount
        );
    }
}
