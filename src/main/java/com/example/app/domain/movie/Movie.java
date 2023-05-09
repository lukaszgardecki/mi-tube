package com.example.app.domain.movie;

import com.example.app.domain.genre.Genre;
import com.example.app.domain.rating.Rating;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String originalTitle;
    private Integer releaseYear;
    private String shortDescription;
    private String description;
    private String youtubeTrailerId;
    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private Set<Rating> ratings = new HashSet<>();
    private boolean promoted;
    private String poster;
    private String director;
    private String writer;
    private String country;
    private Integer runningTime;
    private Long boxOffice;
}
