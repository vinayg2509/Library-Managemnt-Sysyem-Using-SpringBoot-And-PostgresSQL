package com.vinay.controller;

import com.vinay.model.Genre;
import com.vinay.payload.dto.GenreDto;
import com.vinay.services.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        List<GenreDto> getAllGenres=genreService.getAllGeners();
        return new ResponseEntity<>(getAllGenres,HttpStatus.OK);
    }

}
