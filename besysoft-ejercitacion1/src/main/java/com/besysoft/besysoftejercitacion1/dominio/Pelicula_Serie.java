package com.besysoft.besysoftejercitacion1.dominio;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "peliculas")
public class Pelicula_Serie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    @NotNull
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String titulo;
    @Column
    private LocalDate fechaCreacion;
    @DecimalMin(value = "1.0", message = "La calificación permitida es de 1 hasta 5")
    @DecimalMax(value = "5.0", message = "La calificación permitida es de 1 hasta 5")
    @Column
    private double calificacion;

    @ManyToOne()
    //de esta manera no devuelve el genero
    @JsonBackReference(value = "genero-pelicula")
    private Genero genero;
    @ManyToMany(mappedBy = "peliculas_series", fetch = FetchType.LAZY)
    @JsonBackReference(value = "personaje-pelicula")
    private List<Personaje> personajes;

    public Pelicula_Serie(Long id, String titulo, LocalDate fechaCreacion, double calificacion) {
        this.id = id;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
        this.personajes = new ArrayList<>();
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
