package com.example.app.web;

import com.example.app.domain.genre.GenreService;
import com.example.app.domain.genre.genre.GenreDto;
import com.example.app.domain.movie.MovieService;
import com.example.app.domain.movie.dto.MovieDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class GenreController {
    private final GenreService genreService;
    private final MovieService movieService;

    public GenreController(GenreService genreService, MovieService movieService) {
        this.genreService = genreService;
        this.movieService = movieService;
    }

    @GetMapping("/genre/{name}")
    public String getGenre(@PathVariable String name, Model model) {
        GenreDto genre = genreService.findGenreByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<MovieDto> movies = movieService.findMoviesByGenreName(name);
        model.addAttribute("jumbotronHeader", genre.getName());
        model.addAttribute("jumbotronDescription", genre.getDescription());
        model.addAttribute("movies", movies);
        return "layout-elements/content/movie-listing";
    }

    @GetMapping("/movie-genres")
    public String getGenreList(Model model) {
        List<GenreDto> genres = genreService.findAllGenres();
        String jumbotronHeader = "Movie genres";
        String jumbotronDescription = "Genres in our catalogue. Choose one for you!";
        model.addAttribute("jumbotronHeader", jumbotronHeader);
        model.addAttribute("jumbotronDescription", jumbotronDescription);
        model.addAttribute("genres", genres);
        return "layout-elements/content/genre-listing";
    }
}
