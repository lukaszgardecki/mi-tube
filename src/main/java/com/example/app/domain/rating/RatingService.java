package com.example.app.domain.rating;

import com.example.app.domain.movie.Movie;
import com.example.app.domain.movie.MovieRepository;
import com.example.app.domain.user.User;
import com.example.app.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {
    public static final int RATING_LOWEST = 1;
    public static final int RATING_HIGHEST = 10;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public RatingService(RatingRepository ratingRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

//    @Transactional
    public void addOrUpdateRating(String userEmail, long movieId, int rating) {
        Rating ratingToSaveOfUpdate = ratingRepository.findByUser_EmailAndMovie_Id(userEmail, movieId).orElseGet(Rating::new);
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        ratingToSaveOfUpdate.setUser(user);
        ratingToSaveOfUpdate.setMovie(movie);
        ratingToSaveOfUpdate.setRating(rating);
        ratingRepository.save(ratingToSaveOfUpdate);
    }

    public Optional<Integer> getUserRatingForMovie(String userEmail, long movieId) {
        return ratingRepository.findByUser_EmailAndMovie_Id(userEmail, movieId)
                .map(Rating::getRating);
    }
}
