package com.besysoft.besysoftejercitacion1.dominio.mapstruct;

import com.besysoft.besysoftejercitacion1.dominio.dto.GeneroDto;
import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GeneroMapper {
    Genero mapToEntity(GeneroDto dto);

    GeneroDto mapToDto(Genero genero);
}
