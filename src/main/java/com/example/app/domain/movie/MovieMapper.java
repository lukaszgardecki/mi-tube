package com.example.app.domain.movie;

import com.example.app.domain.movie.dto.MovieDto;
import com.example.app.domain.rating.Rating;
import com.example.app.domain.rating.RatingService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.OptionalDouble;

public class MovieMapper {

    static MovieDto map(Movie movie) {

        double avgRating = movie.getRatings().stream()
                .map(Rating::getRating)
                .mapToDouble(val -> val)
                .average().orElse(0);
        int ratingCount = movie.getRatings().size();
        String genre = movie.getGenre() == null ? "" : movie.getGenre().getName();
        HashMap<Integer, String> statsPercentage = getStatsPercentage(movie);
        double highestRatePercentage = getHighestRatePercentage(statsPercentage);

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
                ratingCount,
                statsPercentage,
                highestRatePercentage,
                movie.getDirector(),
                movie.getWriter(),
                movie.getCountry(),
                movie.getRunningTime(),
                movie.getBoxOffice()
        );
    }

    private static double getHighestRatePercentage(HashMap<Integer, String> statsPercentage) {
        OptionalDouble max = statsPercentage.values().stream().mapToDouble(Double::valueOf).max();
        return max.isPresent() ? max.getAsDouble() : 0.0;
    }

    private static HashMap<Integer, String> getStatsPercentage(Movie movie) {
        HashMap<Integer, String> statsPercentage = new HashMap<>(RatingService.RATING_HIGHEST);
        int ratingCount = movie.getRatings().size();

        for (int i = RatingService.RATING_LOWEST; i <= RatingService.RATING_HIGHEST; i++) {
            int rating = i;
            String percentage = "0";
            if (ratingCount != 0) {
                long amount = movie.getRatings().stream().filter(rat -> rat.getRating() == rating).count();
                BigDecimal bigDecimal = BigDecimal.valueOf(amount * 100.0 / ratingCount);
                percentage = getStringPercentage(bigDecimal);
            }
            statsPercentage.put(rating, percentage);
        }
        return statsPercentage;
    }

    private static String getStringPercentage(BigDecimal bigDecimal) {
        String percentage;
        boolean lessThanOne = bigDecimal.compareTo(BigDecimal.ONE) < 0;
        boolean zero = bigDecimal.compareTo(BigDecimal.ZERO) == 0;

        if (!zero && lessThanOne) {
            double result = bigDecimal.setScale(1, RoundingMode.HALF_UP).doubleValue();
            percentage = String.format("%.1f", result);
        } else {
            double result = bigDecimal.setScale(0, RoundingMode.HALF_UP).doubleValue();
            percentage = String.format("%.0f", result);
        }
        return percentage;
    }
}
