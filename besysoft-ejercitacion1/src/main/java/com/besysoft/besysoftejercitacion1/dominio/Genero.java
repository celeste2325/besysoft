package com.besysoft.besysoftejercitacion1.dominio;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class Genero {

    private Long id;
    private String nombre;
    private List<Pelicula_Serie> peliculas_seriesAsociadas;

    public Genero(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.peliculas_seriesAsociadas = new ArrayList<>();
    }

    public void addPelicula_serie(Pelicula_Serie...pelicula_serie) {
        Collections.addAll(this.peliculas_seriesAsociadas, pelicula_serie);
    }
    @Override
    public String toString() {
        return "Genero{" +
                "nombre='" + nombre + '\'' +
                ", peliculas_series=" + peliculas_seriesAsociadas +
                '}';
    }
}
