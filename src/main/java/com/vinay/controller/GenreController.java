package com.vinay.controller;

import com.vinay.exception.GenreException;
import com.vinay.payload.dto.GenreDto;
import com.vinay.services.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/create")
    public ResponseEntity<GenreDto> create(@Valid @RequestBody GenreDto genreDto)
    {
        GenreDto savedGenre=genreService.createGenre (genreDto);
        return new ResponseEntity<>(savedGenre,HttpStatus.CREATED);
    }

    @GetMapping("/getallgenres")
    public  ResponseEntity<?> getAllGenres()
    {
        List<GenreDto> getAllGenres=genreService.getAllGenres();
        return new ResponseEntity<>(getAllGenres,HttpStatus.OK);
    }

    @GetMapping("/getgenrebyid/{id}")
    public ResponseEntity<?> getGenreById(@PathVariable Long id) throws GenreException {
        GenreDto genreDto=genreService.getGenresById(id);
        return new ResponseEntity<>(genreDto,HttpStatus.OK);
    }

}
