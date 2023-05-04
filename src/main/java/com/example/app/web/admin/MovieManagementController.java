package com.example.app.web.admin;

import com.example.app.domain.genre.GenreService;
import com.example.app.domain.genre.genre.GenreDto;
import com.example.app.domain.movie.MovieService;
import com.example.app.domain.movie.dto.MovieDto;
import com.example.app.domain.movie.dto.MovieSaveDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class MovieManagementController {
    private final MovieService movieService;
    private final GenreService genreService;

    public MovieManagementController(MovieService movieService, GenreService genreService) {
        this.movieService = movieService;
        this.genreService = genreService;
    }

    @GetMapping("/movie-add")
    public String addMovieForm(Model model) {
        List<GenreDto> allGenres = genreService.findAllGenres();
        model.addAttribute("genres", allGenres);

        MovieSaveDto movie = new MovieSaveDto();
        model.addAttribute("movie", movie);

        return "layout-elements/content/admin/movie-add-form";
    }

    @PostMapping("/movie-add")
    public String addMovie(MovieSaveDto movie, RedirectAttributes redirectAttributes) {
        movieService.addMovie(movie);

        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "%s movie has been saved".formatted(movie.getTitle())
        );
        return "redirect:/admin";
    }

    @GetMapping("/movie-edit")
    public String editMovieForm(Model model) {
        List<MovieDto> movies = movieService.findAllMovies();
        List<GenreDto> allGenres = genreService.findAllGenres();

        model.addAttribute("movies", movies);
        model.addAttribute("genres", allGenres);
        return "layout-elements/content/admin/movie-edit-form";
    }

    @PostMapping("/movie-edit")
    public String editMovie(MovieSaveDto movie, RedirectAttributes redirectAttributes) {
        movieService.saveEditedMovie(movie);

        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "%s movie has been changed.".formatted(movie.getTitle())
        );
        return "redirect:/admin";
    }

    @GetMapping("/movie-delete")
    public String deleteMovieForm(Model model) {
        List<MovieDto> movies = movieService.findAllMovies();

        model.addAttribute("movies", movies);
        return "layout-elements/content/admin/movie-delete-form";
    }

    @PostMapping("/movie-delete")
    public String deleteMovie(Long movieId, RedirectAttributes redirectAttributes) {
        MovieDto deletedMovie = movieService.deleteMovieById(movieId);
        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "%s movie has been removed.".formatted(deletedMovie.getTitle())
        );
        return "redirect:/admin";
    }
}
