package com.besysoft.besysoftejercitacion1.dominio.mapper;

import com.besysoft.besysoftejercitacion1.dominio.dto.PersonajeDto;
import com.besysoft.besysoftejercitacion1.dominio.entity.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.dominio.entity.Personaje;

import java.util.List;
import java.util.stream.Collectors;

public class PersonajeMapper {

    public static Personaje mapToEntity(PersonajeDto personajeDto) {
        Personaje personaje = new Personaje();

        personaje.setId(personajeDto.getId());
        personaje.setNombre(personajeDto.getNombre());
        personaje.setPeso(personajeDto.getPeso());
        personaje.setEdad(personajeDto.getEdad());
        personaje.setHistoria(personajeDto.getHistoria());
        List<Pelicula_Serie> pelicula_series = personajeDto.getPeliculas_seriesDto() != null
                ? Pelicula_serieMapper.mapListToEntity(personajeDto.getPeliculas_seriesDto())
                : null;

        personaje.setPeliculas_series(pelicula_series);

        return personaje;
    }

    public static PersonajeDto mapToDto(Personaje personaje) {
        PersonajeDto personajeDto = new PersonajeDto();

        personajeDto.setId(personaje.getId());
        personajeDto.setNombre(personaje.getNombre());
        personajeDto.setPeso(personaje.getPeso());
        personajeDto.setEdad(personaje.getEdad());
        personajeDto.setHistoria(personaje.getHistoria());

        return personajeDto;
    }



}
