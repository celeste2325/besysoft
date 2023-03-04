package com.besysoft.besysoftejercitacion1.dominio.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "peliculas")
public class Pelicula_Serie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String titulo;

    private LocalDate fechaCreacion;
    private double calificacion;

    @ManyToOne()
    private Genero genero;
    @ManyToMany(mappedBy = "peliculas_series", fetch = FetchType.LAZY)
    private List<Personaje> personajes;

    public Pelicula_Serie(Long id, String titulo, LocalDate fechaCreacion, double calificacion) {
        this.id = id;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
        this.personajes = new ArrayList<>();
    }

}
