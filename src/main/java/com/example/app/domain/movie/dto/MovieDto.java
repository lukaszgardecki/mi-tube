package com.example.app.domain.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
public class MovieDto {
    private Long id;
    private String title;
    private String originalTitle;
    private Integer releaseYear;
    private String shortDescription;
    private String description;
    private String youtubeTrailer;
    private String genre;
    private boolean promoted;
    private String poster;
    private Double avgRating;
    private int ratingCount;
    private HashMap<Integer, String> statsPercentage;
    private double highestRatePercentage;
}
