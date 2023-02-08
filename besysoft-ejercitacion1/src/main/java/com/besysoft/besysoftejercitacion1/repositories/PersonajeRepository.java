package com.besysoft.besysoftejercitacion1.repositories;

import com.besysoft.besysoftejercitacion1.dominio.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonajeRepository extends CrudRepository<Personaje, Long> {
    Personaje buscarPersonajePorNombreYedad(Personaje personaje);

    List<Personaje> obtenerTodos();

    List<Personaje> buscarPersonajesPorNombreOrEdad(String nombre, int edad);

    List<Personaje> buscarPersonajesPorRangoDeEdad(int desde, int hasta);

    Personaje AltaPersonaje(Personaje newPersonaje);

    Personaje modificarPersonaje(Personaje personajeAmodificar, Personaje personajeNew);

    Personaje buscarPersonajePorId(Long id);


}
