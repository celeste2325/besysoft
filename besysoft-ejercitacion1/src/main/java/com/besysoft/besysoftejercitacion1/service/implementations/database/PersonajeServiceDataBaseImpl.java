package com.besysoft.besysoftejercitacion1.service.implementations.database;

import com.besysoft.besysoftejercitacion1.dominio.entity.Personaje;
import com.besysoft.besysoftejercitacion1.repositories.database.PersonajeRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.PersonajeService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.ElPersonajeExisteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PersonajeInexistenteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "database")
@Slf4j
public class PersonajeServiceDataBaseImpl implements PersonajeService {
    private final PersonajeRepository personajeRepository;

    public PersonajeServiceDataBaseImpl(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personaje> obtenerTodos() {
        return this.personajeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personaje> buscarPersonajesPorNombreOrEdad(String nombre, int edad) {
        log.info("Busqueda por nombre o edad" + "Nombre: " + nombre + "Edad: " + edad);
        return this.personajeRepository.findByNombreOrEdad(nombre, edad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personaje> buscarPersonajesPorRangoDeEdad(int desde, int hasta) {
        log.info("Busqueda por rango de edad" + "Desde: " + desde + "Hasta: " + hasta);
        return this.personajeRepository.findByEdadBetween(desde, hasta);
    }

    @Override
    @Transactional(readOnly = false)
    public Personaje altaPersonaje(Personaje newPersonaje) throws ElPersonajeExisteException {
        Optional<Personaje> personaje = this.personajeRepository.findByNombre(newPersonaje.getNombre());
        if (personaje.isPresent()) {
            if (!personaje.get().getId().equals(newPersonaje.getId()))
                throw new ElPersonajeExisteException("Ya existe un personaje con misma nombre");
        }
        return this.personajeRepository.save(newPersonaje);
    }

    @Override
    @Transactional(readOnly = false)
    public Personaje updatePersonaje(Personaje newPersonaje, Long id) throws PersonajeInexistenteException, ElPersonajeExisteException {
        Personaje personajeEncontrado = this.personajeRepository.findById(id).orElse(null);
        if (personajeEncontrado != null) {
            personajeEncontrado.setNombre(newPersonaje.getNombre());
            personajeEncontrado.setEdad(newPersonaje.getEdad());
            personajeEncontrado.setPeso(newPersonaje.getPeso());
            personajeEncontrado.setHistoria(newPersonaje.getHistoria());
            //valida que las peliculas que se quieren asociar al personaje no esten cargadas
            newPersonaje.getPeliculas_series().forEach(pelicula ->
                    {
                        //encuentra aquellas peliculas q no estan cargados al personaje y las agrega
                        if (!personajeEncontrado.getPeliculas_series().contains(pelicula)) {
                            personajeEncontrado.getPeliculas_series().add(pelicula);
                        }
                    }
            );
            //para reutilizar la validacion de unique
            return this.altaPersonaje(personajeEncontrado);
        }
        throw new PersonajeInexistenteException("El id ingresado no existe");
    }
}
