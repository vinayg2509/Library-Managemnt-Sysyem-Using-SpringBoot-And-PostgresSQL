package com.vinay.services.impl;

import com.vinay.mapper.GenreMapper;
import com.vinay.model.Genre;
import com.vinay.payload.dto.GenreDto;
import com.vinay.repository.GenreRepository;
import com.vinay.services.GenreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class GenreServiceImpl implements GenreService
{
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    @Override


    public GenreDto createGenre(GenreDto genreDto)
    {
        Genre create = genreMapper.toEntity(genreDto);
        Genre saveGenre=genreRepository.save(create);
        return genreMapper.toDto(saveGenre);
    }

    @Override
    public List<GenreDto> getAllGeners() {
        return genreRepository.findAll().stream().map(genreMapper::toDto)
                .collect(Collectors.toList());
    }
}
