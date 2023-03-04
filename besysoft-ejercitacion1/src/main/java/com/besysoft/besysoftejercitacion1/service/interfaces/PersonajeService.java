package com.besysoft.besysoftejercitacion1.service.interfaces;

import com.besysoft.besysoftejercitacion1.dominio.entity.Personaje;
import com.besysoft.besysoftejercitacion1.excepciones.ErrorDeBusquedaException;
import com.besysoft.besysoftejercitacion1.excepciones.IdInexistenteException;
import com.besysoft.besysoftejercitacion1.excepciones.PersonajeExisteException;

import java.util.List;

public interface PersonajeService {
    List<Personaje> obtenerTodos();

    List<Personaje> buscarPersonajesPorNombreOrEdad(String nombre, int edad) throws ErrorDeBusquedaException;

    List<Personaje> buscarPersonajesPorRangoDeEdad(int desde, int hasta);

    Personaje altaPersonaje(Personaje newPersonaje) throws PersonajeExisteException;

    Personaje updatePersonaje(Personaje newPersonaje, Long id) throws IdInexistenteException, PersonajeExisteException;
}
