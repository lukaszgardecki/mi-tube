package com.example.app.domain.genre;

import com.example.app.domain.genre.genre.GenreDto;
import com.example.app.domain.movie.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private GenreService genreService;

    @Test
    void findGenreByName() {
        //given
        String genreName = "Test name";
        Genre genre = getGenre();
        //when
        when(genreRepository.findByNameIgnoreCase(genreName)).thenReturn(Optional.of(genre));
        String foundGenreName = genreService.findGenreByName(genreName).get().getName();
        //then
        assertThat(foundGenreName).isEqualTo(genreName);
    }

    @Test
    void findAllGenres() {
        //given
        List<Genre> genres = getGenreList();
        List<GenreDto> genresDto = genres.stream().map(GenreMapper::map).toList();
        //when
        when(genreRepository.findAll()).thenReturn(genres);
        List<GenreDto> foundGenres = genreService.findAllGenres();
        //then
        assertThat(foundGenres).isNotNull();
        assertThat(foundGenres.size()).isEqualTo(1);
        assertThat(foundGenres.get(0).getId()).isEqualTo(genresDto.get(0).getId());
        assertThat(foundGenres.get(0).getName()).isEqualTo(genresDto.get(0).getName());
        assertThat(foundGenres.get(0).getDescription()).isEqualTo(genresDto.get(0).getDescription());
    }

    @Test
    void addGenre() {
        //given
        GenreDto genreDto = getGenreDto();
        //when
        doThrow(RuntimeException.class).when(genreRepository).save(any(Genre.class));
        //then
        assertThrows(RuntimeException.class, () -> genreService.addGenre(genreDto));
    }



    @Test
    void shouldDeleteGenreById() {
        //given
        Genre genre = getGenre();
        //when
        when(genreRepository.findById(any(Long.class))).thenReturn(Optional.of(genre));
        GenreDto genreToDelete = genreService.deleteGenreById(genre.getId());
        //then
        assertThat(genreToDelete).isNotNull();
        assertThat(genreToDelete.getId()).isEqualTo(genre.getId());
        assertThat(genreToDelete.getName()).isEqualTo(genre.getName());
        assertThat(genreToDelete.getDescription()).isEqualTo(genre.getDescription());
    }

    private Genre getGenre() {
        Movie movie1 = new Movie();
        Movie movie2 = new Movie();

        Set<Movie> movies = new HashSet<>();
        movies.add(movie1);
        movies.add(movie2);

        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Test name");
        genre.setDescription("Test description");
        genre.setMovies(movies);
        return genre;
    }

    private GenreDto getGenreDto() {
        return GenreMapper.map(getGenre());
    }

    private List<Genre> getGenreList() {
        List<Genre> genres = new ArrayList<>();
        Genre genre = getGenre();
        genres.add(genre);
        return genres;
    }
}