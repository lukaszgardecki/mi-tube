package com.example.app.domain.movie;

import com.example.app.domain.genre.Genre;
import com.example.app.domain.movie.dto.MovieDto;
import com.example.app.domain.movie.dto.MovieSaveDto;
import com.example.app.domain.rating.Rating;
import com.example.app.storage.FileStorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private FileStorageService fileStorageService;
    @InjectMocks
    private MovieService movieService;
    private final List<Movie> movies = createMovieList(10);
    private final List<MovieDto> movieDtos = movies.stream()
            .map(MovieMapper::map).toList();

    @Test
    void findAllMovies() {
        //given
        //when
        when(movieRepository.findAll()).thenReturn(movies);
        List<MovieDto> foundMovies = movieService.findAllMovies();
        //then
        assertThat(foundMovies.size()).isEqualTo(movieDtos.size());
    }

    @Test
    void findAllPromotedMovies() {
        //given
        long amountOfPromotedMovies = movieDtos.stream()
                .filter(MovieDto::isPromoted).count();
        List<Movie> promotedMovies = movies.stream().filter(Movie::isPromoted).toList();
        //when
        when(movieRepository.findAllByPromotedIsTrue()).thenReturn(promotedMovies);
        List<MovieDto> foundPromotedMovies = movieService.findAllPromotedMovies();
        //then
        assertThat(foundPromotedMovies.size()).isEqualTo(amountOfPromotedMovies);
    }

    @Test
    void findMovieById() {
        //given
        int id = 1;
        MovieDto movieDto = movieDtos.get(0);
        //when
        when(movieRepository.findById((long) id)).thenReturn(Optional.ofNullable(movies.get(0)));
        MovieDto movieFoundById = movieService.findMovieById((long) id).get();
        //then
        assertThat(movieFoundById.getId()).isEqualTo(movieDto.getId());
        assertThat(movieFoundById.getTitle()).isEqualTo(movieDto.getTitle());
        assertThat(movieFoundById.getWriter()).isEqualTo(movieDto.getWriter());
        assertThat(movieFoundById.getBoxOffice()).isEqualTo(movieDto.getBoxOffice());
    }

    @Test
    void findMoviesByGenreName() {
        //given
        String genreName = "Horror";
        List<MovieDto> moviesDtosByGenreName = getMoviesDtosByGenreName(genreName);
        List<Movie> moviesByGenreName = getMoviesByGenreName(genreName);
        //when
        when(movieRepository.findAllByGenre_NameIgnoreCase(genreName)).thenReturn(moviesByGenreName);
        List<MovieDto> moviesFoundByGenreName = movieService.findMoviesByGenreName(genreName);
        //then
        assertThat(moviesFoundByGenreName).isNotNull();
        assertThat(moviesFoundByGenreName.size()).isEqualTo(moviesDtosByGenreName.size());
    }

    @Test
    void addMovie() {
        //given
        MovieSaveDto movie = createMovieSaveDto();
        //when
        lenient().doThrow(RuntimeException.class).when(movieRepository).save(any(Movie.class));
        //then
        assertThrows(RuntimeException.class, () -> movieService.addMovie(movie));
    }


    @Test
    void deleteMovieById() {
        //given
        Movie movie = movies.get(0);
        //when
        when(movieRepository.findById(any(Long.class))).thenReturn(Optional.of(movie));
        doNothing().when(fileStorageService).deleteImage(any());
        MovieDto movieToDelete = movieService.deleteMovieById(movie.getId());
        //then
        assertThat(movieToDelete).isNotNull();
        assertThat(movieToDelete.getId()).isEqualTo(movie.getId());
        assertThat(movieToDelete.getTitle()).isEqualTo(movie.getTitle());
        assertThat(movieToDelete.getDescription()).isEqualTo(movie.getDescription());
    }

    @Test
    void findTopMovies() {
        //given
        int size = 10;
        List<Movie> moviesSortedByRatingsAverage = getMoviesSortedByRatingsAverage(size);
        //when
        when(movieRepository.findTopByRating(Pageable.ofSize(size))).thenReturn(moviesSortedByRatingsAverage);
        List<MovieDto> foundTopMovies = movieService.findTopMovies(size);
        List<MovieDto> foundTopMoviesSortedByRatingsAverage = foundTopMovies.stream()
                .sorted(Comparator.comparingDouble(MovieDto::getAvgRating))
                .limit(size)
                .toList();
        //then
        assertThat(foundTopMovies).isNotNull();
        assertThat(foundTopMoviesSortedByRatingsAverage.size())
                .isEqualTo(foundTopMoviesSortedByRatingsAverage.size());
        assertThat(foundTopMoviesSortedByRatingsAverage.get(0).getAvgRating())
                .isEqualTo(moviesSortedByRatingsAverage.get(0).getRatings().stream().map(Rating::getRating).mapToDouble(val -> val).average().orElse(0));
    }

    @Test
    void findTopBoxOfficeMovies() {
        //given
        int size = 10;
        List<Movie> moviesSortedByBoxOffice = getMoviesSortedByBoxOffice(size);
        //when
        when(movieRepository.findTopByBoxOffice(Pageable.ofSize(size))).thenReturn(moviesSortedByBoxOffice);
        List<MovieDto> moviesFoundByTopBoxOffice = movieService.findTopBoxOfficeMovies(size);
        List<MovieDto> moviesFoundByTopBoxOfficeAndSorted = moviesFoundByTopBoxOffice.stream()
                .sorted(Comparator.comparingLong(MovieDto::getBoxOffice))
                .limit(size)
                .toList();
        //then
        assertThat(moviesFoundByTopBoxOffice).isNotNull();
        assertThat(moviesFoundByTopBoxOfficeAndSorted.size())
                .isEqualTo(moviesSortedByBoxOffice.size());
        assertThat(moviesFoundByTopBoxOfficeAndSorted.get(0).getBoxOffice())
                .isEqualTo(moviesSortedByBoxOffice.get(0).getBoxOffice());
    }

    private List<MovieDto> getMoviesDtosByGenreName(String genreName) {
        return movieDtos.stream().filter(movie -> movie.getGenre().equals(genreName)).toList();
    }

    private List<Movie> getMoviesByGenreName(String genreName) {
        return movies.stream().filter(m -> m.getGenre().getName().equals(genreName)).toList();
    }

    private List<Movie> getMoviesSortedByRatingsAverage(int size) {
        return movies.stream()
                .sorted(Comparator.comparingDouble(movie -> movie.getRatings().stream()
                        .map(Rating::getRating)
                        .mapToDouble(val -> val)
                        .average().orElse(0)))
                .limit(size)
                .toList();
    }

    private List<Movie> getMoviesSortedByBoxOffice(int size) {
        return movies.stream()
                .sorted(Comparator.comparingDouble(Movie::getBoxOffice))
                .limit(size)
                .toList();
    }

    private List<Movie> createMovieList(int size) {
        List<Movie> movies = new ArrayList<>(size);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            Movie m = createMovie();
            Set<Movie> genreMovies = new HashSet<>();
            genreMovies.add(new Movie());
            Genre genre = new Genre();
            genre.setMovies(genreMovies);
            genre.setName(random.nextBoolean() ? "Horror" : "Test");
            m.setId(i + 1L);
            m.setPromoted(random.nextBoolean());
            m.setGenre(genre);
            m.setRatings(createRatings(10));
            movies.add(m);
        }
        return movies;
    }

    private Set<Rating> createRatings(int amount) {
        Set<Rating> ratings = new HashSet<>();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            int ratingInt = random.nextInt(10);
            Rating rating = new Rating();
            rating.setRating(ratingInt);
            ratings.add(rating);
        }
        return ratings;
    }

    private Movie createMovie() {
        Movie movie = new Movie();
        movie.setTitle("Test title");
        movie.setOriginalTitle("Test original title");
        movie.setReleaseYear(2023);
        movie.setShortDescription("Test short description");
        movie.setDescription("Test description");
        movie.setYoutubeTrailerId("Test trailer id");
        movie.setPoster("Test poster");
        movie.setDirector("Test director");
        movie.setWriter("Test writer");
        movie.setCountry("Test country");
        movie.setRunningTime(123);
        movie.setBoxOffice(12345L);
        return movie;
    }

    private MovieSaveDto createMovieSaveDto() {
        MovieSaveDto movie = new MovieSaveDto();
        movie.setTitle("Test title");
        movie.setOriginalTitle("Test original title");
        movie.setReleaseYear(2023);
        movie.setShortDescription("Test short description");
        movie.setDescription("Test description");
        movie.setYoutubeTrailer("Test trailer id");
        movie.setGenre("Test genre");
        movie.setPromoted(true);
        movie.setPoster(null);
        movie.setDirector("Test director");
        movie.setWriter("Test writer");
        movie.setCountry("Test country");
        movie.setRunningTime(123);
        movie.setBoxOffice(12345L);
        return movie;
    }
}