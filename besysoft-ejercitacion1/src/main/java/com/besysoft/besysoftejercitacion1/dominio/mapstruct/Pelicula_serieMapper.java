package com.besysoft.besysoftejercitacion1.dominio.mapstruct;

import com.besysoft.besysoftejercitacion1.dominio.dto.Pelicula_serieDto;
import com.besysoft.besysoftejercitacion1.dominio.dto.Pelicula_serieResponseDto;
import com.besysoft.besysoftejercitacion1.dominio.entity.Pelicula_Serie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Pelicula_serieMapper {
    Pelicula_Serie mapToEntity(Pelicula_serieDto dto);

    Pelicula_serieResponseDto mapToDto(Pelicula_Serie pelicula_serie);

    List<Pelicula_serieResponseDto> mapLisToDto(List<Pelicula_Serie> pelicula_series);

}
