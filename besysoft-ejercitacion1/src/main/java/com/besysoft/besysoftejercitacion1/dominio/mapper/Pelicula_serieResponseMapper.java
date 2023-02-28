package com.besysoft.besysoftejercitacion1.dominio.mapper;

import com.besysoft.besysoftejercitacion1.dominio.dto.Pelicula_serieDto;
import com.besysoft.besysoftejercitacion1.dominio.dto.Pelicula_serieResponseDto;
import com.besysoft.besysoftejercitacion1.dominio.entity.Pelicula_Serie;

import java.util.List;
import java.util.stream.Collectors;

public class Pelicula_serieResponseMapper {
    public static Pelicula_serieResponseDto mapToDtoResponse(Pelicula_Serie pelicula_serie) {
        Pelicula_serieResponseDto pelicula_serieResponse = new Pelicula_serieResponseDto();

        pelicula_serieResponse.setTitulo(pelicula_serie.getTitulo());
        pelicula_serieResponse.setCalificacion(pelicula_serie.getCalificacion());
        pelicula_serieResponse.setFechaCreacion(pelicula_serie.getFechaCreacion());

        return pelicula_serieResponse;
    }
    public static List<Pelicula_serieResponseDto> mapLisToDto(List<Pelicula_Serie> pelicula_series) {
        return pelicula_series
                .stream().map(Pelicula_serieResponseMapper::mapToDtoResponse).collect(Collectors.toList());
    }
}
