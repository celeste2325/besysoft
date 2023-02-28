package com.besysoft.besysoftejercitacion1.dominio.mapper;

import com.besysoft.besysoftejercitacion1.dominio.dto.GeneroDto;
import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;

import java.util.List;
import java.util.stream.Collectors;

public class GeneroMapper {
    public static Genero mapToEntity(GeneroDto dto) {
        Genero genero = new Genero();
        genero.setId(dto.getId());
        genero.setNombre(dto.getNombre());
        return genero;
    }

    public static GeneroDto mapToDto(Genero genero) {
        GeneroDto dto = new GeneroDto();
        dto.setId(genero.getId());
        dto.setNombre(genero.getNombre());
        return dto;
    }

    public static List<GeneroDto> mapLisToDto(List<Genero> generos) {
        return generos
                .stream().map(GeneroMapper::mapToDto).collect(Collectors.toList());
    }
}
