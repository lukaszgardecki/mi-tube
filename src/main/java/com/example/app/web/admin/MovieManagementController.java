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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MovieManagementController {
    private final MovieService movieService;
    private final GenreService genreService;

    public MovieManagementController(MovieService movieService, GenreService genreService) {
        this.movieService = movieService;
        this.genreService = genreService;
    }

    @GetMapping("/admin/dodaj-film")
    public String addMovieForm(Model model) {
        List<GenreDto> allGenres = genreService.findAllGenres();
        model.addAttribute("genres", allGenres);

        MovieSaveDto movie = new MovieSaveDto();
        model.addAttribute("movie", movie);

        return "admin/movie-add-form";
    }

    @PostMapping("/admin/dodaj-film")
    public String addMovie(MovieSaveDto movie, RedirectAttributes redirectAttributes) {
        movieService.addMovie(movie);

        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "Film %s został zapisany".formatted(movie.getTitle())
        );
        return "redirect:/admin";
    }

    @GetMapping("/admin/edytuj-film")
    public String editMovieForm(Model model) {
        List<MovieDto> movies = movieService.findAllMovies();
        List<GenreDto> allGenres = genreService.findAllGenres();

        model.addAttribute("movies", movies);
        model.addAttribute("genres", allGenres);
        return "admin/movie-edit-form";
    }

    @PostMapping("/admin/edytuj-film")
    public String editMovie(MovieSaveDto movie, RedirectAttributes redirectAttributes) {
        System.out.println(movie.getPoster().getOriginalFilename());

        movieService.saveEditedMovie(movie);

        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "Film %s został zapisany".formatted(movie.getTitle())
        );
        return "redirect:/admin";
    }
}
