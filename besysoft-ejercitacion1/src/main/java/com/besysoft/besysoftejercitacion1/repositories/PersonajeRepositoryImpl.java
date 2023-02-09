package com.besysoft.besysoftejercitacion1.repositories;

import com.besysoft.besysoftejercitacion1.dominio.Personaje;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PersonajeRepositoryImpl implements PersonajeRepository {

    private final List<Personaje> listaDePersonajes;

    public PersonajeRepositoryImpl() {
        this.listaDePersonajes = new ArrayList<>(
                Arrays.asList(
                        new Personaje(1L, "Keanu Charles Reeves", 58, 58.0, "mejor conocido como Keanu Reeves es un actor y " +
                                "músico canadiense.Es conocido por Interpretar a Neo en Matrix y a John Wick En la trilogía John Wick."),
                        new Personaje(2L, "Jason Momoa", 43, 80.0, " es un actor, actor de voz, escritor, productor y director estadounidense. "),
                        new Personaje(3L, "Laurence Fishburne", 61, 87, "Fishburne nació en Augusta, Georgia")
                )
        );
    }

    @Override
    public Personaje buscarPersonajePorNombreYedad(Personaje personaje) {
        return this.listaDePersonajes.stream().filter(personaje1 -> personaje1.getNombre().equalsIgnoreCase(personaje.getNombre())
                && personaje1.getEdad() == personaje.getEdad()).findFirst().orElse(null);
    }

    @Override
    public List<Personaje> obtenerTodos() {
        return this.listaDePersonajes;
    }

    @Override
    public Personaje buscarPersonajePorId(Long id) {
        return this.listaDePersonajes.stream().filter(personaje -> personaje.getId() == id).findAny().orElse(null);
    }

    @Override
    public List<Personaje> buscarPersonajesPorNombreOrEdad(String nombre, int edad) {
        return this.listaDePersonajes.stream().filter(personaje -> personaje.getNombre().equalsIgnoreCase(nombre) ||
                personaje.getEdad() == edad).collect(Collectors.toList());
    }

    @Override
    public List<Personaje> buscarPersonajesPorRangoDeEdad(int desde, int hasta) {
        return this.listaDePersonajes.stream().filter(personaje -> personaje.getEdad() >= desde && personaje.getEdad() <= hasta).collect(Collectors.toList());
    }

    @Override
    public Personaje AltaPersonaje(Personaje newPersonaje) {
        newPersonaje.setId((long) (this.listaDePersonajes.size() + 1));
        this.listaDePersonajes.add(newPersonaje);
        return newPersonaje;
    }

    @Override
    public Personaje modificarPersonaje(Personaje personajeAmodificar, Personaje personajeNew) {

        personajeAmodificar.setPeso(personajeNew.getPeso());
        personajeAmodificar.setEdad(personajeNew.getEdad());
        personajeAmodificar.setHistoria(personajeNew.getHistoria());
        personajeAmodificar.setNombre(personajeNew.getNombre());

        //valida que las peliculas que se quieren asociar al personaje no esten cargadas
        personajeNew.getPeliculas_series().forEach(pelicula ->
                {
                    //encuentra aquellas peliculas q no estan cargados al personaje y las agrega
                    if (!personajeAmodificar.getPeliculas_series().contains(pelicula)) {

                        //si no esta cargada la pelicula al personaje lo agrega
                        personajeAmodificar.getPeliculas_series().add(pelicula);
                    }
                }
        );
        return personajeAmodificar;
    }


}
