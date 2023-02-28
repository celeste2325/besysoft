package com.besysoft.besysoftejercitacion1.dominio.mapper;

import com.besysoft.besysoftejercitacion1.dominio.dto.PersonajeDto;
import com.besysoft.besysoftejercitacion1.dominio.dto.PersonajeDtoResponse;
import com.besysoft.besysoftejercitacion1.dominio.entity.Personaje;

import java.util.List;
import java.util.stream.Collectors;

public class PersonajeResponseMapper {

    public static PersonajeDtoResponse mapToDtoResponse(Personaje personaje) {
        PersonajeDtoResponse personajeDto = new PersonajeDtoResponse();

        personajeDto.setNombre(personaje.getNombre());
        personajeDto.setPeso(personaje.getPeso());
        personajeDto.setEdad(personaje.getEdad());
        personajeDto.setHistoria(personaje.getHistoria());

        return personajeDto;
    }


    public static List<PersonajeDtoResponse> mapLisToDto(List<Personaje> personajes) {
        return personajes
                .stream().map(PersonajeResponseMapper::mapToDtoResponse).collect(Collectors.toList());
    }

}
