package com.besysoft.besysoftejercitacion1.dominio.mapstruct;

import com.besysoft.besysoftejercitacion1.dominio.dto.PersonajeDto;
import com.besysoft.besysoftejercitacion1.dominio.dto.PersonajeDtoResponse;
import com.besysoft.besysoftejercitacion1.dominio.entity.Personaje;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonajeMapper {
    @Mapping(source = "peliculas_seriesDto", target = "peliculas_series")
    Personaje mapToEntity(PersonajeDto personajeDto);

    PersonajeDtoResponse mapToDtoResponse(Personaje personaje);

    List<PersonajeDtoResponse> mapLisToDto(List<Personaje> personajes);

}
