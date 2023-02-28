package com.besysoft.besysoftejercitacion1.dominio.mapper;

import com.besysoft.besysoftejercitacion1.dominio.dto.Pelicula_serieDto;
import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import com.besysoft.besysoftejercitacion1.dominio.entity.Pelicula_Serie;

import java.util.List;
import java.util.stream.Collectors;

public class Pelicula_serieMapper {
    public static Pelicula_Serie mapToEntity(Pelicula_serieDto dto) {
        Pelicula_Serie pelicula_serie = new Pelicula_Serie();

        pelicula_serie.setId(dto.getId());
        pelicula_serie.setTitulo(dto.getTitulo());
        pelicula_serie.setCalificacion(dto.getCalificacion());
        pelicula_serie.setFechaCreacion(dto.getFechaCreacion());
        Genero genero = dto.getGenero() != null ? GeneroMapper.mapToEntity(dto.getGenero()) : null;
        pelicula_serie.setGenero(genero);
        return pelicula_serie;
    }

    public static Pelicula_serieDto mapToDto(Pelicula_Serie pelicula_serie) {
        Pelicula_serieDto pelicula_serieDto = new Pelicula_serieDto();
        pelicula_serieDto.setId(pelicula_serie.getId());
        pelicula_serieDto.setTitulo(pelicula_serie.getTitulo());
        pelicula_serieDto.setFechaCreacion(pelicula_serie.getFechaCreacion());
        pelicula_serieDto.setCalificacion(pelicula_serie.getCalificacion());

        return pelicula_serieDto;
    }

    public static List<Pelicula_Serie> mapListToEntity(List<Pelicula_serieDto> pelicula_serieDtos) {
        return pelicula_serieDtos
                .stream().map(Pelicula_serieMapper::mapToEntity).collect(Collectors.toList());
    }
}
