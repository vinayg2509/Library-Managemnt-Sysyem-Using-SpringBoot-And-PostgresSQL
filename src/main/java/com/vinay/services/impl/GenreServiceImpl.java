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
public class GenreServiceImpl implements GenreService
{
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    @Override

    @Transactional
    public GenreDto createGenre(GenreDto genreDto)
    {

        Genre genre=Genre.builder()
                .code(genreDto.getCode())
                .name(genreDto.getName())
                .displayOrder(genreDto.getDisplayOrder())
                .description(genreDto.getDescription())
                .active(true)
                .build();
        if(genreDto.getParentGenreId()!=null)
        {
         Genre parentGenre=genreRepository.findById(genreDto.getParentGenreId()).get();
         genre.setParentGenre(parentGenre);
        }
        Genre saveGenre=genreRepository.save(genre);

        return genreMapper.toDto(saveGenre);
    }

    @Override
    public List<GenreDto> getAllGeners() {
        return genreRepository.findAll().stream().map(GenreMapper::toDto)
                .collect(Collectors.toList());
    }
}
