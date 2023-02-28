package com.besysoft.besysoftejercitacion1.dominio.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class PersonajeDtoResponse {
    private String nombre;
    private int edad;
    private double peso;
    private String historia;
}
