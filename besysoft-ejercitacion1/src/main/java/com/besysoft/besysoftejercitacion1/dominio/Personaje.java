package com.besysoft.besysoftejercitacion1.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity(name = "personajes")
public class Personaje implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;
    @Column(nullable = false, unique = true)
    private int edad;
    @Column
    private double peso;
    @Column
    private String historia;
    @Column
    @JoinColumn(name = "peliculaId")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "personajesAsociadosApeliculas",
            joinColumns = @JoinColumn(name = "personajeId"),
            inverseJoinColumns = @JoinColumn(name = "peliculaId")
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

    public Personaje() {

    }

    public void addPelicula_serie(Pelicula_Serie... pelicula_serie) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personaje personaje = (Personaje) o;
        return edad == personaje.edad && Objects.equals(nombre, personaje.nombre);
    }

}
