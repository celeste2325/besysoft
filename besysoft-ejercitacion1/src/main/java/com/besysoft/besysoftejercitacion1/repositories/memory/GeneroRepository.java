package com.besysoft.besysoftejercitacion1.repositories.memory;

import com.besysoft.besysoftejercitacion1.dominio.Genero;

public interface GeneroRepository {
    Genero altaGenero(Genero newGenero);

    Genero updateGenero(Genero generoAmodificar, Genero newGenero);

    Genero buscarGeneroPorId(Long id);

    Genero buscarGeneroPorNombre(String nombreGenero);
}
