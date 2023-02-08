package com.besysoft.besysoftejercitacion1.repositories;

import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface PeliculaRepository extends CrudRepository<Pelicula_Serie, Long> {
    List<Pelicula_Serie> obtenerTodos();

    Object buscarPeliculaPorTituloOrGenero(String titulo, String genero);

    List<Pelicula_Serie> buscarPeliculasPorRangoDeFecha(LocalDate desde, LocalDate hasta);

    List<Pelicula_Serie> buscarPeliculasPorRangoDeCalificacion(double desde, double hasta);

    Pelicula_Serie buscarPeliculaPorId(Long id);

    Pelicula_Serie buscarPeliculaPorTitulo(Pelicula_Serie pelicula_serie);

    Pelicula_Serie altaPelicula(Pelicula_Serie peliculaNew);

    Pelicula_Serie updatePelicula(Pelicula_Serie pliculaAModificar, Pelicula_Serie peliculaNew);
}
