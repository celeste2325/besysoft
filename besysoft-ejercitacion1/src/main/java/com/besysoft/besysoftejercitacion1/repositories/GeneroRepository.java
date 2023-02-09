package com.besysoft.besysoftejercitacion1.repositories;

import com.besysoft.besysoftejercitacion1.dominio.Genero;
import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;

import java.util.List;

public interface GeneroRepository {
    Genero altaGenero(Genero newGenero);

    Genero updateGenero(Genero generoAmodificar, Genero newGenero);

    Genero buscarGeneroPorId(Long id);

    Genero buscarGeneroPorNombre(String nombreGenero);
}
