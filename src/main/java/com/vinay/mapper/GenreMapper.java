package com.vinay.mapper;

import com.vinay.payload.dto.GenreDto;
import com.vinay.model.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenreMapper {


        public static GenreDto toDto(Genre savedGenre)
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
                                .map(GenreMapper::toDto)
                                .collect(Collectors.toList())
                );
            }

            return dto;
        }


}