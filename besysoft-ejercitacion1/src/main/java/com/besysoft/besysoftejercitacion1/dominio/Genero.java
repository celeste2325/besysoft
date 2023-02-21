package com.besysoft.besysoftejercitacion1.dominio;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "generos")
public class Genero implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@JsonIgnore
    private Long id;
    @NotNull
    @NotEmpty
    @Column(nullable = false, length = 50, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "genero",fetch = FetchType.LAZY)
    @JsonManagedReference(value = "genero-pelicula")
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
                "nombre='" + nombre + '\'' +
                ", peliculas_series=" + peliculas_series +
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
