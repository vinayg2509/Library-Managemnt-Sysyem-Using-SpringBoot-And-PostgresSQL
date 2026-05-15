package com.vinay.mapper;

import com.vinay.payload.dto.GenreDto;
import com.vinay.model.Genre;
import com.vinay.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenreMapper {

    private final GenreRepository genreRepository;

    public  GenreDto toDto(Genre savedGenre)
        {
            if (savedGenre == null) return null;

            GenreDto dto=GenreDto.builder()
                    .id(savedGenre.getId())
                    .code(savedGenre.getCode())
                    .name(savedGenre.getName())
                    .description(savedGenre.getDescription())
                    .displayOrder(savedGenre.getDisplayOrder())
                    .active(true)
                    .createdAt(savedGenre.getCreatedAt())
                    .updatedAt(savedGenre.getUpdatedAt() )
                    .build();

            if(savedGenre.getParentGenre()!=null)
            {
                dto.setParentGenreId(savedGenre.getParentGenre().getId());
                dto.setParentGenreName(savedGenre.getParentGenre().getName());
            }

            if (savedGenre.getSubGenre() != null && ! savedGenre.getSubGenre().isEmpty()) {

                dto.setSubGenre(
                        savedGenre.getSubGenre().stream()
                                .filter(Genre::getActive)
                                .map(this::toDto)
                                .collect(Collectors.toList())
                );
            }

            return dto;
        }

        public Genre toEntity(GenreDto genreDto)
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
               genreRepository.findById(genreDto.getParentGenreId()).ifPresent(genre::setParentGenre);
            }
            return genre;
        }


}