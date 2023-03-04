package com.besysoft.besysoftejercitacion1.datos;

import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import com.besysoft.besysoftejercitacion1.dominio.entity.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.dominio.entity.Personaje;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class DatosDummy {

    private List<Genero> generos;
    private List<Pelicula_Serie> peliculas_series;
    private List<Personaje> personajes;
    private Pelicula_Serie peliculaMock;
    private Pelicula_Serie peliculaUpdateMock;
    private Genero aveturaMock;
    private Genero aventura2Mock;
    private Genero dramaMock;


    public DatosDummy() {
        this.generos = new ArrayList<>();
        this.peliculas_series = new ArrayList<>();
        this.personajes = new ArrayList<>();
        this.llenarArreglos();
    }

    public static List<Pelicula_Serie> getPeliculasPorFiltro() {
        return new ArrayList<>(Arrays.asList(
                new Pelicula_Serie(6L, "avatar2", LocalDate.of(2011, 04, 17), 3.0),
                new Pelicula_Serie(7L, "enredadas", LocalDate.of(2011, 10, 17), 5.0)
        ));
    }

    public static Personaje getPersonaje1() {
        return new Personaje(6L, "Anthony Hopkins", 85, 80.0, "es un actor, director y productor galés");

    }

    public static Personaje getPersonaje1Update() {
        Personaje personaje = new Personaje(6L, "Anthony HopkinsModificado", 85, 80.0, "es un actor, director y productor galés");
        personaje.getPeliculas_series().add(new Pelicula_Serie(8L, "avatar4", LocalDate.of(2011, 04, 17), 3.0));
        return personaje;

    }

    public static Personaje getPersonaje2() {
        return new Personaje(7L, "Anthony Hopkins", 90, 80.0, "es un actor, director y productor galés");
    }

    public static List<Personaje> getPersonajesPorFiltro() {
        return new ArrayList<>(Arrays.asList(
                new Personaje(7L, "olivia colman", 49, 75.0, "es una actriz inglesa. Conocida por sus papeles cómicos y dramáticos en cine y televisión"),
                new Personaje(8L, "Olivia Williams", 49, 76.0, "es una actriz británica que ha aparecido en películas y programas de televisión británicos y estadounidenses.")
        ));
    }

    public void llenarArreglos() {
        //creacion de personajes
        Personaje keanuReeves = new Personaje(2L, "Keanu Charles Reeves", 58, 58.0, "mejor conocido como Keanu Reeves es un actor y músico canadiense.Es conocido por Interpretar a Neo en Matrix y a John Wick En la trilogía John Wick.");
        Personaje LaurenceFishburne = new Personaje(4L, "Laurence Fishburne", 61, 87, "Fishburne nació en Augusta, Georgia");
        //creacion de peliculas-series

        Pelicula_Serie matrix = new Pelicula_Serie(2L, "Matrix", LocalDate.of(1999, 01, 01), 5.0);
        Pelicula_Serie johnWick = new Pelicula_Serie(3L, "John Wick", LocalDate.of(2014, 01, 01), 4.7);
        Pelicula_Serie gameofThrones = new Pelicula_Serie(4L, "Game of Thrones", LocalDate.of(2011, 04, 17), 5.0);
        peliculaMock = new Pelicula_Serie(5L, "avatar", LocalDate.of(2011, 04, 17), 3.0);
        peliculaUpdateMock = new Pelicula_Serie(5L, "avatar", LocalDate.of(2020, 01, 27), 4.8);
        //agrega peliculas a los personajes
        // keanuReeves.addPelicula_serie(matrix, johnWick);
        //LaurenceFishburne.addPelicula_serie(matrix);
        //jasonMomoa.addPelicula_serie(gameofThrones);

        //creacion generos
        Genero accion = new Genero(2L, "accion");
        aventura2Mock = new Genero(3L, "aventura2");
        dramaMock = new Genero(4L, "drama");
        this.aveturaMock = new Genero(5L, "aventura");
        peliculaMock.setGenero(aventura2Mock);
        peliculaUpdateMock.setGenero(aventura2Mock);


        //agrega peliculas-series a los generos
        //accion.addPelicula_serie(matrix, johnWick);
        /*aventura.addPelicula_serie(gameofThrones);*/

        //llenado de arreglos generos, peliculas_series, personajes.

        Collections.addAll(this.generos, aventura2Mock, accion);
        Collections.addAll(this.peliculas_series, matrix, johnWick, gameofThrones);
        Collections.addAll(this.personajes, keanuReeves, LaurenceFishburne);
    }


}
