package com.besysoft.besysoftejercitacion1.repositories.database;

import com.besysoft.besysoftejercitacion1.dominio.Genero;
import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PeliculaRepository extends JpaRepository<Pelicula_Serie, Long> {
    List<Pelicula_Serie> findByTituloOrGenero(String titulo, Genero genero);

    List<Pelicula_Serie> findByFechaCreacionBetween(LocalDate desde, LocalDate hasta);

    List<Pelicula_Serie> findByCalificacionBetween(double desde, double hasta);
}
