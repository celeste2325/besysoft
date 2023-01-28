package com.besysoft.besysoftejercitacion1.utilidades;

import com.besysoft.besysoftejercitacion1.dominio.Genero;
import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.dominio.Personaje;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Service
public class DatosDummy {

    private List<Genero> generos;
    private List<Pelicula_Serie> peliculas_series;
    private List<Personaje> personajes;


    public DatosDummy() {
        this.generos = new ArrayList<>();
        this.peliculas_series = new ArrayList<>();
        this.personajes = new ArrayList<>();
        this.llenarArreglos();
    }

    public void llenarArreglos() {
        //creacion de personajes
        Personaje keanuReeves = new Personaje(1L,"Keanu Charles Reeves",58,58.0,"mejor conocido como Keanu Reeves es un actor y músico canadiense.Es conocido por Interpretar a Neo en Matrix y a John Wick En la trilogía John Wick.");
        Personaje jasonMomoa = new Personaje(2L,"Jason Momoa",43,80.0," es un actor, actor de voz, escritor, productor y director estadounidense. ");

        //creacion de peliculas-series
        Pelicula_Serie matrix = new Pelicula_Serie(1L,"Matrix",LocalDate.of(1999,01,01),5.0);
        Pelicula_Serie johnWick = new Pelicula_Serie(2L,"John Wick",LocalDate.of(2014,01,01),4.7);
        Pelicula_Serie gameofThrones = new Pelicula_Serie(3L,"Game of Thrones",LocalDate.of(2011,04,17),5.0);
        Pelicula_Serie aquaman  = new Pelicula_Serie(4L,"Aquaman",LocalDate.of(2018,12,21),3);

       //agrega peliculas a los personajes
        keanuReeves.addPelicula_serie(matrix,johnWick);
        jasonMomoa.addPelicula_serie(aquaman,gameofThrones);

        //creacion generos
        Genero accion = new Genero(1L,"accion");
        Genero aventura = new Genero(2L,"aventura");

        //agrega peliculas-series a los generos
        accion.addPelicula_serie(aquaman,matrix,johnWick);
        aventura.addPelicula_serie(aquaman,gameofThrones);

        //llenado de arreglos generos, peliculas_series, personajes.

        Collections.addAll(this.generos, aventura,accion);
        Collections.addAll(this.peliculas_series, matrix,johnWick,aquaman,gameofThrones);
        Collections.addAll(this.personajes, keanuReeves, jasonMomoa);
    }
    //devuelve Object porq en un caso retorna distintos tipos de datos, de acuerdo al if
    public Object getPersonajesPorNombreOrEdad(String nombre, int edad) {
        if (!nombre.equalsIgnoreCase("") && edad != 0) {
            return "buscar por nombre o por edad";
        }
        return this.personajes.stream().filter(personaje -> personaje.getNombre().equalsIgnoreCase(nombre) ||
                personaje.getEdad() == edad);
    }

    //devuelve Object porq en un caso retorna distintos tipos de datos, de acuerdo al if
    public Object getPeliculasByTituloOrGenero(String titulo, String genero) {
        if (!titulo.equalsIgnoreCase("") && !genero.equalsIgnoreCase("")) {
            return "buscar por genero o por titulo";
        }
        else if (!titulo.equalsIgnoreCase("") && genero.equalsIgnoreCase("")) {
            return this.peliculas_series.stream().filter(pelicula_serie -> pelicula_serie.getTitulo().equalsIgnoreCase(titulo)).distinct().findFirst().get();
        }
        else {
            return this.generos.stream().filter(genero1 -> genero1.getNombre().equalsIgnoreCase(genero)).collect(Collectors.toList()).get(0).getPeliculas_seriesAsociadas();
        }
    }

    public Object getPeliculasByFecha(LocalDate desde, LocalDate hasta) {
        return this.peliculas_series.stream().filter(pelicula_serie -> pelicula_serie.getFechaCreacion().isAfter(desde) &&
                pelicula_serie.getFechaCreacion().isBefore(hasta));
    }

    public Object getPersonajesPorRangoDeEdad(int desde, int hasta) {
        return this.personajes.stream().filter(personaje -> personaje.getEdad() >= desde && personaje.getEdad() <= hasta);
    }

    public Object getPeliculasByRangoDeCalificacion(double desde, double hasta) {
        return this.peliculas_series.stream().filter(pelicula_serie -> pelicula_serie.getCalificacion() >= desde && pelicula_serie.getCalificacion() <= hasta);
    }
}
