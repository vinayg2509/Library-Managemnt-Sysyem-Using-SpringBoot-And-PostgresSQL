package com.vinay.services;

import com.vinay.payload.dto.GenreDto;
import java.util.List;
public interface GenreService {
     GenreDto createGenre(GenreDto genreDto);

     List<GenreDto> getAllGeners();
}
