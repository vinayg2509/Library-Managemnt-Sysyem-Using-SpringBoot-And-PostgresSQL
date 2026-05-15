package com.vinay.services.impl;

import com.vinay.exception.GenreException;
import com.vinay.mapper.GenreMapper;
import com.vinay.model.Genre;
import com.vinay.payload.dto.GenreDto;
import com.vinay.repository.GenreRepository;
import com.vinay.services.GenreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream().map(genreMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public GenreDto getGenresById(Long id) throws GenreException {
        Genre genre= genreRepository.findById(id)
                .orElseThrow(() -> new GenreException("No genre found for the given id: " + id));
        return genreMapper.toDto(genre);
    }
 
    @Override
    public void deleteGenre(Long genreId) {

    }

    @Override
    public void hardDelete(Long genreId) {

    }

    @Override
    public GenreDto updateGenre(Long genreId, GenreDto genreDto) {
        return null;
    }

    @Override
    public List<GenreDto> getAllActiveGenreWithSubGenre() {
        return List.of();
    }

    @Override
    public List<GenreDto> getTopLevelGenre() {
        return List.of();
    }

    @Override
    public Long getAllTotalActiveGenre() {
        return 0L;
    }

    @Override
    public Long getActiveBookCountByGenreId(Long genreId) {
        return 0L;
    }
}
