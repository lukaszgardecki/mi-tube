package com.example.app.domain.genre;

import com.example.app.domain.genre.genre.GenreDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GenreMapperTest {

    @Test
    void map() {
        //given
        Genre genre = getGenre();
        //when
        GenreDto genreDto = GenreMapper.map(genre);
        //then
        assertThat(genreDto).isNotNull();
        assertThat(genreDto.getId()).isEqualTo(genre.getId());
        assertThat(genreDto.getName()).isEqualTo(genre.getName());
        assertThat(genreDto.getDescription()).isEqualTo(genre.getDescription());
    }

    private Genre getGenre() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Test name");
        genre.setDescription("Test description");
        return genre;
    }
}