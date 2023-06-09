package com.example.app.web;

import com.example.app.domain.movie.MovieService;
import com.example.app.domain.movie.dto.MovieDto;
import com.example.app.domain.rating.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class MovieController {
    private final MovieService movieService;
    private final RatingService ratingService;

    public MovieController(MovieService movieService, RatingService ratingService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
    }

    @GetMapping("/movie/{id}")
    public String getMovie(@PathVariable long id, Model model, Authentication authentication) {
        MovieDto movie = movieService.findMovieById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("movie", movie);

        if (authentication != null) {
            String currentUserEmail = authentication.getName();
            Integer rating = ratingService.getUserRatingForMovie(currentUserEmail, id).orElse(0);
            model.addAttribute("userRating", rating);
        }
        return "layout-elements/content/movie";
    }

    @GetMapping("/top10")
    public String findTop10(Model model) {
        List<MovieDto> top10Movies = movieService.findTopMovies(10);
        String jumbotronHeader = "TOP 10";
        String jumbotronDescription = "Top rated movies";
        model.addAttribute("jumbotronHeader", jumbotronHeader);
        model.addAttribute("jumbotronDescription", jumbotronDescription);
        model.addAttribute("movies", top10Movies);
        return "layout-elements/content/top10";
    }

    @GetMapping("/top-boxoffice")
    public String findTopBoxOffice(Model model) {
        List<MovieDto> topBoxOfficeMovies = movieService.findTopBoxOfficeMovies(10);
        String jumbotronHeader = "TOP 10 Box Office";
        String jumbotronDescription = "Highest-grossing movies";
        model.addAttribute("jumbotronHeader", jumbotronHeader);
        model.addAttribute("jumbotronDescription", jumbotronDescription);
        model.addAttribute("movies", topBoxOfficeMovies);
        return "layout-elements/content/top-boxoffice";
    }
}
