package com.besysoft.besysoftejercitacion1.dominio.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class Pelicula_serieResponseDto {
    private String titulo;
    private LocalDate fechaCreacion;
    private double calificacion;
}
