package com.besysoft.besysoftejercitacion1.service.implementations;

import com.besysoft.besysoftejercitacion1.dominio.Personaje;
import com.besysoft.besysoftejercitacion1.repositories.PersonajeRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.PersonajeService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.BuscarPorEdadOPorNombreException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.ElPersonajeExisteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.NombreYEdadSonCamposObligatoriosException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PersonajeInexistenteException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PersonajeServiceImpl implements PersonajeService {
    private final PersonajeRepository personajeRepository;

    public PersonajeServiceImpl(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    @Override
    public List<Personaje> obtenerTodos() {
        return this.personajeRepository.obtenerTodos();
    }

    @Override
    public List<Personaje> buscarPersonajesPorNombreOrEdad(String nombre, int edad) throws BuscarPorEdadOPorNombreException {
        if (!nombre.equalsIgnoreCase("") && edad != 0) {
            throw new BuscarPorEdadOPorNombreException("buscar por nombre o por edad");
        }
        return this.personajeRepository.buscarPersonajesPorNombreOrEdad(nombre, edad);
    }

    @Override
    public List<Personaje> buscarPersonajesPorRangoDeEdad(int desde, int hasta) {
        return this.personajeRepository.buscarPersonajesPorRangoDeEdad(desde,hasta);
    }

    @Override
    public Personaje altaPersonaje(Personaje newPersonaje) throws ElPersonajeExisteException, NombreYEdadSonCamposObligatoriosException {
        //se asume campos nombre y edad son obligatorios
        if (newPersonaje.getNombre() != null && newPersonaje.getEdad() != 0) {
            if (this.personajeRepository.buscarPersonajePorNombreYedad(newPersonaje) != null) {
                throw new ElPersonajeExisteException("Ya existe el personaje");
            }
            return this.personajeRepository.AltaPersonaje(newPersonaje);
        }
        throw new NombreYEdadSonCamposObligatoriosException("Los campos: nombre y edad son obligatorios");
    }

    @Override
    public Personaje updatePersonaje(Personaje newPersonaje, Long id) throws PersonajeInexistenteException, ElPersonajeExisteException {
        Personaje personajeEncontradoById = this.personajeRepository.buscarPersonajePorId(id);
        Personaje personajeEncontradoByNombreAndEdad = this.personajeRepository.buscarPersonajePorNombreYedad(newPersonaje);

        if (personajeEncontradoById != null) {
            if (personajeEncontradoByNombreAndEdad != null && !Objects.equals(personajeEncontradoByNombreAndEdad.getId(), id)) {
                throw new ElPersonajeExisteException("Ya existe otro personaje con mismo nombre y edad");
            }
            return this.personajeRepository.modificarPersonaje(personajeEncontradoById,newPersonaje);
        }
        throw new PersonajeInexistenteException("El personaje que intenta modificar no existe");
    }
}
