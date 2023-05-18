package com.example.app.domain.genre;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void isCreated() {
        assertThat(genreRepository).isNotNull();
    }

    @Test
    void findByNameIgnoreCase() {
        //given
        Genre genre = getGenre();
        //when
        Genre savedGenre = genreRepository.save(genre);
        Genre foundGenre = genreRepository.findByNameIgnoreCase("test name").get();
        //then
        assertThat(savedGenre).isNotNull();
        assertThat(foundGenre).isEqualTo(savedGenre);
    }

    private Genre getGenre() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Test name");
        genre.setDescription("Test description");
        return genre;
    }
}