package com.example.app.domain.movie.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MovieSaveDto {
    private Long id;
    private String title;
    private String originalTitle;
    private Integer releaseYear;
    private String shortDescription;
    private String description;
    private String youtubeTrailer;
    private String genre;
    private boolean promoted;
    private MultipartFile poster;
    private String director;
    private String writer;
    private String country;
    private Integer runningTime;
    private Long boxOffice;
}
