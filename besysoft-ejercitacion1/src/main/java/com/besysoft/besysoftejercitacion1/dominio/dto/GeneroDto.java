package com.besysoft.besysoftejercitacion1.dominio.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class GeneroDto {
    private Long id;
    @NotNull
    @NotEmpty
    private String nombre;
}
