package com.besysoft.besysoftejercitacion1.repositories;

import com.besysoft.besysoftejercitacion1.dominio.Genero;
import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GeneroRepositoryImpl implements GeneroRepository {
    private final List<Genero> listaGeneros;


    public GeneroRepositoryImpl() {
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
        generoAmodificar.setNombre(newGenero.getNombre());
        //valida que las peliculas que se quieren asociar al genero no esten cargados
        newGenero.getPeliculas_seriesAsociadas().forEach(pelicula_serie ->
                {
                    //encuentra aquellas peliculas q no estan cargadas y las agrega
                    if (!generoAmodificar.getPeliculas_seriesAsociadas().contains(pelicula_serie)) {
                        generoAmodificar.getPeliculas_seriesAsociadas().add(pelicula_serie);
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
    public Genero buscarGeneroPorNombre(String nombreGenero) {
        return this.listaGeneros.stream().filter(genero1 -> genero1.getNombre().equalsIgnoreCase(nombreGenero)).findAny().orElse(null);
    }

}
