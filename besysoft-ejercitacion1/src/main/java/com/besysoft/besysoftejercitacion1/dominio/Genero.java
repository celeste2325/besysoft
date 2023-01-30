package com.besysoft.besysoftejercitacion1.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class Genero {

    @JsonIgnore
    private Long id;
    private String nombre;
    private List<Pelicula_Serie> peliculas_seriesAsociadas;

    public Genero(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.peliculas_seriesAsociadas = new ArrayList<>();
    }

    public void addPelicula_serie(Pelicula_Serie... pelicula_serie) {
        Collections.addAll(this.peliculas_seriesAsociadas, pelicula_serie);
    }

    @Override
    public String toString() {
        return "Genero{" +
                "nombre='" + nombre + '\'' +
                ", peliculas_series=" + peliculas_seriesAsociadas +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genero genero = (Genero) o;
        return Objects.equals(nombre, genero.nombre);
    }

}
