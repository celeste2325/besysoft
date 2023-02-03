package com.besysoft.besysoftejercitacion1.repositories;

import com.besysoft.besysoftejercitacion1.dominio.Genero;
import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class GeneroRepositoryImpl implements GeneroRepository {
    private final List<Genero> listaGeneros;
    private final PeliculaRepository peliculaRepository;

    public GeneroRepositoryImpl(@Lazy PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
        this.listaGeneros = new ArrayList<>(
                Arrays.asList(
                        new Genero(1L, "accion"),
                        new Genero(2L, "aventura")
                )
        );
    }

    @Override
    public Genero altaGenero(Genero newGenero) {
        newGenero.setId((long) (this.listaGeneros.size() + 1));
        this.listaGeneros.add(newGenero);
        return newGenero;
    }

    @Override
    public Genero updateGenero(Genero generoAmodificar, Genero newGenero) {
        if (generoAmodificar.getNombre() != null) {
            generoAmodificar.setNombre(newGenero.getNombre());
        }
        //valida que las peliculas que se quieren asociar al genero no esten cargados
        newGenero.getPeliculas_seriesAsociadas().forEach(pelicula_serie ->
                {
                    //encuentra aquellas peliculas q no estan cargadas y las agrega
                    if (!generoAmodificar.getPeliculas_seriesAsociadas().contains(pelicula_serie)) {

                        //busca la pelicula por su titulo, esto para no tener que pasar el obj completo por el body
                        Pelicula_Serie peliculaEncontradaByTitulo = this.peliculaRepository.buscarPeliculaPorTitulo(pelicula_serie);

                        if (peliculaEncontradaByTitulo == null) {
                            peliculaEncontradaByTitulo = this.peliculaRepository.altaPelicula(pelicula_serie);
                            //agregar metodo dar alta cuando lo saque del controller y pase al service
                        }
                        generoAmodificar.getPeliculas_seriesAsociadas().add(peliculaEncontradaByTitulo);
                    }
                }
        );
        return generoAmodificar;
    }

    @Override
    public Genero buscarGeneroPorId(Long id) {
        return this.listaGeneros.stream().filter(genero -> genero.getId() == id).findAny().orElse(null);
    }

    @Override
    public Genero buscarGeneroPorNombre(Genero genero) {
        //se asume que no debe existir 2 generos con mismo nombre
        return this.listaGeneros.stream().filter(genero1 -> genero1.getNombre().equalsIgnoreCase(genero.getNombre())).findAny().orElse(null);
    }
}
