package com.besysoft.besysoftejercitacion1.dominio.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Pelicula_serieDto {
    private Long id;
    private String titulo;
    private LocalDate fechaCreacion;
    private double calificacion;
    private GeneroDto genero;
}
