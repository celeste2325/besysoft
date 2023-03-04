package com.besysoft.besysoftejercitacion1.dominio.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class Pelicula_serieDto {
    private Long id;
    @NotNull
    @NotEmpty
    private String titulo;
    private LocalDate fechaCreacion;
    @DecimalMin(value = "1.0", message = "La calificación permitida es de 1 hasta 5")
    @DecimalMax(value = "5.0", message = "La calificación permitida es de 1 hasta 5")
    private double calificacion;
    private GeneroDto genero;
}
