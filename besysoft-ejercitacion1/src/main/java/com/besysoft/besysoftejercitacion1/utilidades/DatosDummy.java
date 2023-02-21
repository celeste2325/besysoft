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
@Deprecated
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
        Personaje keanuReeves = new Personaje(1L, "Keanu Charles Reeves", 58, 58.0, "mejor conocido como Keanu Reeves es un actor y músico canadiense.Es conocido por Interpretar a Neo en Matrix y a John Wick En la trilogía John Wick.");
        Personaje jasonMomoa = new Personaje(2L, "Jason Momoa", 43, 80.0, " es un actor, actor de voz, escritor, productor y director estadounidense. ");
        Personaje LaurenceFishburne = new Personaje(3L, "Laurence Fishburne", 61, 87, "Fishburne nació en Augusta, Georgia");
        //creacion de peliculas-series
        Pelicula_Serie matrix = new Pelicula_Serie(1L, "Matrix", LocalDate.of(1999, 01, 01), 5.0);
        Pelicula_Serie johnWick = new Pelicula_Serie(2L, "John Wick", LocalDate.of(2014, 01, 01), 4.7);
        Pelicula_Serie gameofThrones = new Pelicula_Serie(3L, "Game of Thrones", LocalDate.of(2011, 04, 17), 5.0);
        Pelicula_Serie aquaman = new Pelicula_Serie(4L, "Aquaman", LocalDate.of(2018, 12, 21), 4.0);

        //agrega peliculas a los personajes
        keanuReeves.addPelicula_serie(matrix, johnWick);
        LaurenceFishburne.addPelicula_serie(matrix);
        jasonMomoa.addPelicula_serie(aquaman, gameofThrones);

        //creacion generos
        Genero accion = new Genero(1L, "accion");
        Genero aventura = new Genero(2L, "aventura");

        //agrega peliculas-series a los generos
        accion.addPelicula_serie(aquaman, matrix, johnWick);
        aventura.addPelicula_serie(aquaman, gameofThrones);

        //llenado de arreglos generos, peliculas_series, personajes.

        Collections.addAll(this.generos, aventura, accion);
        Collections.addAll(this.peliculas_series, matrix, johnWick, aquaman, gameofThrones);
        Collections.addAll(this.personajes, keanuReeves, jasonMomoa, LaurenceFishburne);
    }

    //devuelve Object porq en un caso retorna distintos tipos de datos, de acuerdo al if
    public Object buscarPersonajesPorNombreOrEdad(String nombre, int edad) {
        if (!nombre.equalsIgnoreCase("") && edad != 0) {
            return "buscar por nombre o por edad";
        }
        return this.personajes.stream().filter(personaje -> personaje.getNombre().equalsIgnoreCase(nombre) ||
                personaje.getEdad() == edad);
    }

    //devuelve Object porq en un caso retorna distintos tipos de datos, de acuerdo al if
    public Object buscarPeliculasPorTituloOrGenero(String titulo, String genero) {
        if (!titulo.equalsIgnoreCase("") && !genero.equalsIgnoreCase("")) {
            return "buscar por genero o por titulo";
        } else if (!titulo.equalsIgnoreCase("") && genero.equalsIgnoreCase("")) {
            return this.peliculas_series.stream().filter(pelicula_serie -> pelicula_serie.getTitulo().equalsIgnoreCase(titulo)).distinct().findFirst().get();
        } else {
            return this.generos.stream().filter(genero1 -> genero1.getNombre().equalsIgnoreCase(genero)).collect(Collectors.toList()).get(0).getPeliculas_series();
        }
    }

    public List<Pelicula_Serie> buscarPeliculasPorRangoDeFecha(LocalDate desde, LocalDate hasta) {
        return this.peliculas_series.stream().filter(pelicula_serie -> pelicula_serie.getFechaCreacion().isAfter(desde) &&
                pelicula_serie.getFechaCreacion().isBefore(hasta)).collect(Collectors.toList());
    }

    public Object buscarPersonajesPorRangoDeEdad(int desde, int hasta) {
        return this.personajes.stream().filter(personaje -> personaje.getEdad() >= desde && personaje.getEdad() <= hasta);
    }

    public List<Pelicula_Serie> buscarPeliculasPorRangoDeCalificacion(double desde, double hasta) {
        return this.peliculas_series.stream().filter(pelicula_serie -> pelicula_serie.getCalificacion() >= desde && pelicula_serie.getCalificacion() <= hasta).collect(Collectors.toList());
    }

    //se valida que no exista pelicula con mismo titulo
    public Pelicula_Serie buscarPeliculaPorTitulo(Pelicula_Serie pelicula_serie) {
        return this.getPeliculas_series().stream().filter(pelicula ->
                pelicula.getTitulo().
                        equalsIgnoreCase(pelicula_serie.getTitulo())).findAny().orElse(null);
    }

    //se asume que no debe existir 2 peresonajes con mismo nombre y misma edad
    public Personaje buscarPersonajePorNombreYedad(Personaje personaje) {
        return this.getPersonajes().stream().filter(personaje1 -> personaje1.getNombre().equalsIgnoreCase(personaje.getNombre())
                && personaje1.getEdad() == personaje.getEdad()).findFirst().orElse(null);
    }

    public Pelicula_Serie buscarPeliculaPorId(Long id) {
        return this.peliculas_series.stream().filter(pelicula_serie -> pelicula_serie.getId() == id).findAny().orElse(null);
    }

    public Personaje buscarPersonajePorId(Long id) {
        return this.personajes.stream().filter(personaje -> personaje.getId() == id).findAny().orElse(null);
    }

    public Genero buscarGeneroPorId(Long id) {
        return this.getGeneros().stream().filter(genero -> genero.getId() == id).findAny().orElse(null);
    }

    public Genero buscarGeneroPorNombre(Genero genero) {
        //se asume que no debe existir 2 generos con mismo nombre
        return this.getGeneros().stream().filter(genero1 -> genero1.getNombre().equalsIgnoreCase(genero.getNombre())).findAny().orElse(null);
    }

}
