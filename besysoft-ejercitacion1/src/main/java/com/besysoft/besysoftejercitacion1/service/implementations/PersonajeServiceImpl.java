package com.besysoft.besysoftejercitacion1.service.implementations;

import com.besysoft.besysoftejercitacion1.dominio.Personaje;
import com.besysoft.besysoftejercitacion1.repositories.memory.PersonajeRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.PersonajeService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.BuscarPorEdadOPorNombreException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.ElPersonajeExisteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PersonajeInexistenteException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "memory")
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
        return this.personajeRepository.buscarPersonajesPorRangoDeEdad(desde, hasta);
    }

    @Override
    public Personaje altaPersonaje(Personaje newPersonaje) throws ElPersonajeExisteException {
        if (this.personajeRepository.buscarPersonajePorNombre(newPersonaje) != null) {
            throw new ElPersonajeExisteException("Ya existe el personaje");
        }
        return this.personajeRepository.AltaPersonaje(newPersonaje);

    }

    @Override
    public Personaje updatePersonaje(Personaje newPersonaje, Long id) throws PersonajeInexistenteException, ElPersonajeExisteException {
        Personaje personajeEncontradoById = this.personajeRepository.buscarPersonajePorId(id);
        Personaje personajeEncontradoByNombre = this.personajeRepository.buscarPersonajePorNombre(newPersonaje);

        if (personajeEncontradoById != null) {
            if (personajeEncontradoByNombre != null && !Objects.equals(personajeEncontradoByNombre.getId(), id)) {
                throw new ElPersonajeExisteException("Ya existe otro personaje con mismo nombre");
            }
            return this.personajeRepository.modificarPersonaje(personajeEncontradoById, newPersonaje);
        }
        throw new PersonajeInexistenteException("El personaje que intenta modificar no existe");
    }
}
