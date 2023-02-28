package com.besysoft.besysoftejercitacion1.repositories.memory;

import com.besysoft.besysoftejercitacion1.dominio.entity.Personaje;

import java.util.List;

public interface PersonajeRepository {
    Personaje buscarPersonajePorNombre(Personaje personaje);

    List<Personaje> obtenerTodos();

    List<Personaje> buscarPersonajesPorNombreOrEdad(String nombre, int edad);

    List<Personaje> buscarPersonajesPorRangoDeEdad(int desde, int hasta);

    Personaje AltaPersonaje(Personaje newPersonaje);

    Personaje modificarPersonaje(Personaje personajeAmodificar, Personaje personajeNew);

    Personaje buscarPersonajePorId(Long id);


}
