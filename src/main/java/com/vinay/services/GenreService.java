package com.vinay.services;

import com.vinay.exception.GenreException;
import com.vinay.payload.dto.GenreDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface GenreService {
     GenreDto createGenre(GenreDto genreDto);

     List<GenreDto> getAllGenres();

     GenreDto getGenresById(Long genreId) throws GenreException;

     void deleteGenre(Long genreId);

     void hardDelete(Long genreId);

     GenreDto updateGenre(Long genreId, GenreDto genreDto)throws GenreException;

     List<GenreDto> getAllActiveGenreWithSubGenre();

     List<GenreDto> getTopLevelGenre();

//     Page<GenreDto> searchGenre(String searchTerm,Pageable pageable);

    Long getAllTotalActiveGenre();

    Long getActiveBookCountByGenreId(Long genreId);
}
