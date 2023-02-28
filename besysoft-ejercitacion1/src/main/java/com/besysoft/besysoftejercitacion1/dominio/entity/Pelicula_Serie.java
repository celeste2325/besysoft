package com.besysoft.besysoftejercitacion1.dominio.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "peliculas")
public class Pelicula_Serie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String titulo;

    private LocalDate fechaCreacion;
    @DecimalMin(value = "1.0", message = "La calificación permitida es de 1 hasta 5")
    @DecimalMax(value = "5.0", message = "La calificación permitida es de 1 hasta 5")
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
