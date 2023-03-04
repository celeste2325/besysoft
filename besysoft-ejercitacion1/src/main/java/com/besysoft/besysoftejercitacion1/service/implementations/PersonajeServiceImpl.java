package com.besysoft.besysoftejercitacion1.service.implementations;

import com.besysoft.besysoftejercitacion1.dominio.entity.Personaje;
import com.besysoft.besysoftejercitacion1.repositories.memory.PersonajeRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.PersonajeService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.ErrorDeBusquedaException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.IdInexistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PersonajeExisteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "memory")
@Slf4j
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
    public List<Personaje> buscarPersonajesPorNombreOrEdad(String nombre, int edad) throws ErrorDeBusquedaException {
        if (!nombre.equalsIgnoreCase("") && edad != 0) {
            throw new ErrorDeBusquedaException("buscar por nombre o por edad");
        }
        log.info("Busqueda por nombre o edad" + "Nombre: " + nombre + "Edad: " + edad);
        return this.personajeRepository.buscarPersonajesPorNombreOrEdad(nombre, edad);
    }

    @Override
    public List<Personaje> buscarPersonajesPorRangoDeEdad(int desde, int hasta) {
        log.info("Busqueda por rango de edad" + "Desde: " + desde + "Hasta: " + hasta);
        return this.personajeRepository.buscarPersonajesPorRangoDeEdad(desde, hasta);
    }

    @Override
    public Personaje altaPersonaje(Personaje newPersonaje) throws PersonajeExisteException {
        if (this.personajeRepository.buscarPersonajePorNombre(newPersonaje) != null) {
            throw new PersonajeExisteException("Ya existe el personaje");
        }
        return this.personajeRepository.AltaPersonaje(newPersonaje);

    }

    @Override
    public Personaje updatePersonaje(Personaje newPersonaje, Long id) throws IdInexistenteException, PersonajeExisteException {
        Personaje personajeEncontradoById = this.personajeRepository.buscarPersonajePorId(id);
        Personaje personajeEncontradoByNombre = this.personajeRepository.buscarPersonajePorNombre(newPersonaje);

        if (personajeEncontradoById != null) {
            if (personajeEncontradoByNombre != null && !Objects.equals(personajeEncontradoByNombre.getId(), id)) {
                throw new PersonajeExisteException("Ya existe otro personaje con mismo nombre");
            }
            return this.personajeRepository.modificarPersonaje(personajeEncontradoById, newPersonaje);
        }
        throw new IdInexistenteException("El personaje que intenta modificar no existe");
    }
}
