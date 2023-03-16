package com.example.app.domain.genre;

import com.example.app.domain.genre.genre.GenreDto;

public class GenreMapper {

    static GenreDto map(Genre genre) {
        return new GenreDto(
                genre.getId(),
                genre.getName(),
                genre.getDescription()
        );
    }
}
