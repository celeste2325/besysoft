package com.besysoft.besysoftejercitacion1.service.implementations.database;

import com.besysoft.besysoftejercitacion1.dominio.Personaje;
import com.besysoft.besysoftejercitacion1.repositories.database.PersonajeRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.PersonajeService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.ElPersonajeExisteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PersonajeInexistenteException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "database")
public class PersonajeServiceDataBaseImpl implements PersonajeService {
    private final PersonajeRepository personajeRepository;

    public PersonajeServiceDataBaseImpl(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    @Override
    public List<Personaje> obtenerTodos() {
        return this.personajeRepository.findAll();
    }

    @Override
    public List<Personaje> buscarPersonajesPorNombreOrEdad(String nombre, int edad) {
        return this.personajeRepository.findByNombreOrEdad(nombre, edad);
    }

    @Override
    public List<Personaje> buscarPersonajesPorRangoDeEdad(int desde, int hasta) {
        return this.personajeRepository.findByEdadBetween(desde, hasta);
    }

    @Override
    public Personaje altaPersonaje(Personaje newPersonaje) throws ElPersonajeExisteException {
        try {
            return this.personajeRepository.save(newPersonaje);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new ElPersonajeExisteException("Ya existe un personaje con misma nombre");
        }
    }

    @Override
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
