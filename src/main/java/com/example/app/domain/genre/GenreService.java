package com.example.app.domain.genre;

import com.example.app.domain.genre.genre.GenreDto;

import java.util.Optional;

public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Optional<GenreDto> findGenreByName(String name) {
        return genreRepository.findByNameIgnoreCase(name)
                .map(GenreMapper::map);
    }
}
