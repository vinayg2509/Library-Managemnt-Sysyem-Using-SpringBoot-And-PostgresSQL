package com.vinay.controller;

import com.vinay.exception.GenreException;
import com.vinay.model.Genre;
import com.vinay.payload.dto.GenreDto;
import com.vinay.payload.response.ApiResponse;
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

    @PutMapping("/updategenre/{genreId}")
    private ResponseEntity<GenreDto> updateGenre
            (@PathVariable Long genreId,
             @Valid @RequestBody GenreDto genreDto) throws GenreException {
            try {
                GenreDto updatedGenre=genreService.updateGenre(genreId,genreDto);
                return ResponseEntity.ok(updatedGenre);
            }catch (GenreException e)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
    }

    @DeleteMapping("soft-delete-genre/{genreId}")
    public ResponseEntity<ApiResponse> softDelete(@PathVariable Long genreId)
    {
        try {
            genreService.deleteGenre(genreId);
            return ResponseEntity.ok(new ApiResponse("Genre softly Deleted",true));
        } catch (GenreException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(e.getMessage(),false));
        }
    }

    @DeleteMapping("hard-delete-genre/{genreId}")
    public ResponseEntity<ApiResponse> hardDelete(@PathVariable Long genreId)
    {
        try {
            genreService.deleteGenre(genreId);
            return ResponseEntity.ok(new ApiResponse("Genre Hardly Deleted",true));
        } catch (GenreException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(e.getMessage(),false));
        }
    }

    @GetMapping("/top-level-genre")
    public ResponseEntity<?> getTopLevelGenre() throws GenreException {
        List<GenreDto> genreDto=genreService.getTopLevelGenre();
        return  ResponseEntity.ok(genreDto);
    }

    @GetMapping("/count-active-genre")
    public ResponseEntity<?> getAllTotalGenre() throws GenreException {
       Long activeGenre=genreService.getAllTotalActiveGenre();
        return new ResponseEntity<>(activeGenre,HttpStatus.OK);
    }

    @GetMapping("/tota-book-count-by-genre/{genreId}")
    public ResponseEntity<?> getTotalBook(@PathVariable Long genreId) throws GenreException {
        Long totalBookByGenre=genreService.getActiveBookCountByGenre(genreId);
        return new ResponseEntity<>(totalBookByGenre,HttpStatus.OK);
    }

}
