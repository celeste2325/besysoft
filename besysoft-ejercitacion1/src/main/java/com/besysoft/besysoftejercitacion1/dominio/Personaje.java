package com.besysoft.besysoftejercitacion1.dominio;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class Personaje {
    private Long id;
    private String nombre;
    private int edad;
    private double peso;
    private String historia;
    private List<Pelicula_Serie> peliculas_series;

    public Personaje(Long id, String nombre, int edad, double peso, String historia) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
        this.peliculas_series = new ArrayList<>();
    }

    public void addPelicula_serie(Pelicula_Serie...pelicula_serie) {
        Collections.addAll(this.peliculas_series, pelicula_serie);
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", peso=" + peso +
                ", historia='" + historia + '\'' +
                ", peliculas=" + peliculas_series +
                '}';

    }
}
