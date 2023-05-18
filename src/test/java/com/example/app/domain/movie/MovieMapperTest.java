package com.example.app.domain.movie;

import com.example.app.domain.genre.Genre;
import com.example.app.domain.movie.dto.MovieDto;
import com.example.app.domain.rating.Rating;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.*;

class MovieMapperTest {

    @Test
    void map() {
        //given
        Movie movie = getMovie();
        double avgRating = movie.getRatings().stream()
                .map(Rating::getRating)
                .mapToDouble(val -> val)
                .average().orElse(0);

        //when
        MovieDto movieDto = MovieMapper.map(movie);
        //then
        assertThat(movieDto).isNotNull();
        assertThat(movieDto.getId()).isEqualTo(movie.getId());
        assertThat(movieDto.getTitle()).isEqualTo(movie.getTitle());
        assertThat(movieDto.getOriginalTitle()).isEqualTo(movie.getOriginalTitle());
        assertThat(movieDto.getReleaseYear()).isEqualTo(movie.getReleaseYear());
        assertThat(movieDto.getShortDescription()).isEqualTo(movie.getShortDescription());
        assertThat(movieDto.getDescription()).isEqualTo(movie.getDescription());
        assertThat(movieDto.getYoutubeTrailer()).isEqualTo(movie.getYoutubeTrailerId());
        assertThat(movieDto.getGenre()).isEqualTo(movie.getGenre().getName());
        assertThat(movieDto.isPromoted()).isEqualTo(movie.isPromoted());
        assertThat(movieDto.getPoster()).isEqualTo(movie.getPoster());
        assertThat(movieDto.getAvgRating()).isEqualTo(avgRating);
        assertThat(movieDto.getRatingCount()).isEqualTo(movie.getRatings().size());

        assertThat(movieDto.getDirector()).isEqualTo(movie.getDirector());
        assertThat(movieDto.getWriter()).isEqualTo(movie.getWriter());
        assertThat(movieDto.getCountry()).isEqualTo(movie.getCountry());
        assertThat(movieDto.getRunningTime()).isEqualTo(movie.getRunningTime());
        assertThat(movieDto.getBoxOffice()).isEqualTo(movie.getBoxOffice());
    }

    private Movie getMovie() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test title");
        movie.setOriginalTitle("Test original title");
        movie.setReleaseYear(2023);
        movie.setShortDescription("Test short description");
        movie.setDescription("Test description");
        movie.setYoutubeTrailerId("Test trailer id");
        movie.setGenre(new Genre());
        movie.setRatings(new HashSet<>());
        movie.setPromoted(true);
        movie.setPoster("Test poster");
        movie.setDirector("Test director");
        movie.setWriter("Test writer");
        movie.setCountry("Test country");
        movie.setRunningTime(123);
        movie.setBoxOffice(12345L);
        return movie;
    }
}