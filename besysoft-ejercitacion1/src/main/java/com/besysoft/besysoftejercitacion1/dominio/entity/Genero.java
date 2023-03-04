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
@Table(name = "generos")
public class Genero implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "genero", fetch = FetchType.LAZY)
    private List<Pelicula_Serie> peliculas_series;

    public Genero(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.peliculas_series = new ArrayList<>();

    }

    public void addPelicula_serie(Pelicula_Serie... pelicula_serie) {
        Collections.addAll(this.peliculas_series, pelicula_serie);
    }

    @Override
    public String toString() {
        return "Genero{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
