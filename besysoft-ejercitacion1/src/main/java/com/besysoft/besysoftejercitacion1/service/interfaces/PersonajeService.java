package com.besysoft.besysoftejercitacion1.service.interfaces;

import com.besysoft.besysoftejercitacion1.dominio.Personaje;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.BuscarPorEdadOPorNombreException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.ElPersonajeExisteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.NombreYEdadSonCamposObligatoriosException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PersonajeInexistenteException;

import java.util.List;

public interface PersonajeService {
    List<Personaje> obtenerTodos();

    List<Personaje> buscarPersonajesPorNombreOrEdad(String nombre, int edad) throws BuscarPorEdadOPorNombreException;

    List<Personaje> buscarPersonajesPorRangoDeEdad(int desde, int hasta);

    Personaje altaPersonaje(Personaje newPersonaje) throws ElPersonajeExisteException, NombreYEdadSonCamposObligatoriosException;

    Personaje updatePersonaje(Personaje newPersonaje, Long id) throws PersonajeInexistenteException, ElPersonajeExisteException;
}
