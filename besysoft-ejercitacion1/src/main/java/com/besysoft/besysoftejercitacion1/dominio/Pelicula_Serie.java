package com.besysoft.besysoftejercitacion1.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Pelicula_Serie {

    @JsonIgnore
    private Long id;
    private String titulo;
    private LocalDate fechaCreacion;
    private double calificacion;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Personaje> personajesAsociados;

    public Pelicula_Serie(Long id, String titulo,LocalDate fechaCreacion, double calificacion) {
        this.id = id;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
        this.personajesAsociados = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Pelicula_Serie{" +
                "titulo='" + titulo + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", calificacion=" + calificacion +
                ", personajesAsociados=" + personajesAsociados +
                '}';
    }
}
