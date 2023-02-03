package com.besysoft.besysoftejercitacion1.repositories;

import com.besysoft.besysoftejercitacion1.dominio.Personaje;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PersonajeRepository {
    Personaje buscarPersonajePorNombreYedad(Personaje personaje);
    List<Personaje> obtenerTodos();
    List<Personaje> buscarPersonajesPorNombreOrEdad(String nombre, int edad);
    List<Personaje> buscarPersonajesPorRangoDeEdad(int desde, int hasta);
    Personaje AltaPersonaje(Personaje newPersonaje);
    Personaje modificarPersonaje(Personaje personajeAmodificar,Personaje personajeNew);
    Personaje buscarPersonajePorId(Long id);


}
