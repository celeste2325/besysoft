package com.besysoft.besysoftejercitacion1.dominio.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "personajes")
public class Personaje implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nombre;

    private int edad;

    private double peso;

    private String historia;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "personaje_pelicula",
            joinColumns = @JoinColumn(name = " personaje_id"),
            inverseJoinColumns = @JoinColumn(name = "pelicula_id")
    )
    private List<Pelicula_Serie> peliculas_series;

    public Personaje(Long id, String nombre, int edad, double peso, String historia) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
        this.peliculas_series = new ArrayList<>();
    }

    public void addPelicula_serie(Pelicula_Serie... pelicula_serie) {
        Collections.addAll(this.peliculas_series, pelicula_serie);
    }

}
