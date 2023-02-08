package com.besysoft.besysoftejercitacion1.repositories;

import com.besysoft.besysoftejercitacion1.dominio.Genero;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GeneroRepository extends CrudRepository<Genero, Long> {
    Genero altaGenero(Genero newGenero);

    Genero updateGenero(Genero generoAmodificar, Genero newGenero);

    Genero buscarGeneroPorId(Long id);

    Genero buscarGeneroPorNombre(Genero genero);

    List<Genero>obtenerTodos();
}
