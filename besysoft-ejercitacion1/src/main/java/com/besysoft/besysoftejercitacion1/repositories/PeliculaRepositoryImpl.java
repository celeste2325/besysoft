package com.besysoft.besysoftejercitacion1.repositories;

import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.dominio.Personaje;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class PeliculaRepositoryImpl implements PeliculaRepository {
    private final List<Pelicula_Serie> peliculas_series;
    private final PersonajeRepository personajeRepository;

    public PeliculaRepositoryImpl(@Lazy PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
        this.peliculas_series = new ArrayList<>(
                Arrays.asList(
                        new Pelicula_Serie(1L, "Matrix", LocalDate.of(1999, 01, 01), 5.0),
                        new Pelicula_Serie(2L, "John Wick", LocalDate.of(2014, 01, 01), 4.7),
                        new Pelicula_Serie(3L, "Game of Thrones", LocalDate.of(2011, 04, 17), 5.0),
                        new Pelicula_Serie(4L, "Aquaman", LocalDate.of(2018, 12, 21), 3)
                )
        );
    }

    @Override
    public List<Pelicula_Serie> obtenerTodos() {
        return this.peliculas_series;
    }

    @Override
    public List<Pelicula_Serie> buscarPeliculasPorTituloOrGenero(String titulo, String genero) {
        return null;
    }

    @Override
    public List<Pelicula_Serie> buscarPeliculasPorRangoDeFecha(LocalDate desde, LocalDate hasta) {
        return this.peliculas_series.stream().filter(pelicula_serie -> pelicula_serie.getFechaCreacion().isAfter(desde) &&
                pelicula_serie.getFechaCreacion().isBefore(hasta)).collect(Collectors.toList());
    }

    @Override
    public List<Pelicula_Serie> buscarPeliculasPorRangoDeCalificacion(double desde, double hasta) {
        return this.peliculas_series.stream().filter(pelicula_serie -> pelicula_serie.getCalificacion() >= desde && pelicula_serie.getCalificacion() <= hasta).collect(Collectors.toList());
    }

    @Override
    public Pelicula_Serie buscarPeliculaPorId(Long id) {
        return this.peliculas_series.stream().filter(pelicula_serie -> Objects.equals(pelicula_serie.getId(), id)).findAny().orElse(null);
    }

    @Override
    public Pelicula_Serie buscarPeliculaPorTitulo(Pelicula_Serie pelicula_serie) {
        return this.peliculas_series.stream().filter(pelicula ->
                pelicula.getTitulo().
                        equalsIgnoreCase(pelicula_serie.getTitulo())).findAny().orElse(null);
    }

    @Override
    public Pelicula_Serie altaPelicula(Pelicula_Serie peliculaNew) {
        peliculaNew.setId((long) (this.peliculas_series.size() + 1));
        this.peliculas_series.add(peliculaNew);
        return peliculaNew;
    }

    @Override
    public Pelicula_Serie updatePelicula(Pelicula_Serie peliculaEncontrada, Pelicula_Serie peliculaNew) {
        //si vienen datos nulos o vacios no se sobreescriben
        if (peliculaNew.getTitulo() != null) {
            peliculaEncontrada.setTitulo(peliculaNew.getTitulo());
        }
        if (peliculaNew.getCalificacion() != 0.0) {
            peliculaEncontrada.setCalificacion(peliculaNew.getCalificacion());
        }
        if (peliculaNew.getFechaCreacion() != null) {
            peliculaEncontrada.setFechaCreacion(peliculaNew.getFechaCreacion());
        }

        //editar lista de pesonajes asociados a la pelicula
        peliculaNew.getPersonajesAsociados().forEach(personaje ->
                {
                    //encuentra aquellos personajes q no estan cargados a la pelicula
                    if (!peliculaEncontrada.getPersonajesAsociados().contains(personaje)) {
                        //busca el personaje con ese nombre y edad (asumiendo q son sus datos unicos) esto para no tener que pasar el obj completo por el body
                        Personaje personajeEncontrado = this.personajeRepository.buscarPersonajePorNombreYedad(personaje);

                        //permite agregar el personaje a su lista en caso de q no exista
                        if (personajeEncontrado == null) {
                            personajeEncontrado = this.personajeRepository.AltaPersonaje(personaje);

                        }
                        //si no esta cargado el personaje a la pelicula lo agrega
                        peliculaEncontrada.getPersonajesAsociados().add(personajeEncontrado);
                        //actualiza tambien la lista de peliculas que tiene ese personaje, siempre que ya no exista la pelicula en la lista.
                        if (!personajeEncontrado.getPeliculas_series().contains(peliculaEncontrada)) {
                            personajeEncontrado.addPelicula_serie(peliculaEncontrada);
                        }
                    }
                }
        );

        return peliculaEncontrada;
    }
}
