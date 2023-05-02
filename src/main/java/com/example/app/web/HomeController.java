package com.example.app.web;

import com.example.app.domain.movie.MovieService;
import com.example.app.domain.movie.dto.MovieDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final MovieService movieService;

    public HomeController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping("/")
    String home(Model model) {
        List<MovieDto> promotedMovies = movieService.findAllPromotedMovies();
        model.addAttribute("heading", "Promoted movies");
        model.addAttribute("movies", promotedMovies);
        return "movie-listing";
    }
}
