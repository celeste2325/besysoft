package com.besysoft.besysoftejercitacion1.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "Peliculas")
public class Pelicula_Serie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String titulo;

    @Column
    private LocalDate fechaCreacion;
    @Column
    private double calificacion;
    @ManyToMany(mappedBy = "peliculas_series", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Personaje> personajes;

    public Pelicula_Serie(Long id, String titulo, LocalDate fechaCreacion, double calificacion) {
        this.id = id;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
        this.personajes = new ArrayList<>();
    }

    public Pelicula_Serie() {

    }

    @Override
    public String toString() {
        return "Pelicula_Serie{" +
                "titulo='" + titulo + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", calificacion=" + calificacion +
                ", personajesAsociados=" + personajes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pelicula_Serie that = (Pelicula_Serie) o;
        return Objects.equals(titulo, that.titulo);
    }
}
