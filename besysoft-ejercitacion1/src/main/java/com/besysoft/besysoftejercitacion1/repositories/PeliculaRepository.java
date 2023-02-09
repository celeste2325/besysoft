package com.besysoft.besysoftejercitacion1.repositories;

import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;

import java.time.LocalDate;
import java.util.List;

public interface PeliculaRepository {
    List<Pelicula_Serie> obtenerTodos();

    List<Pelicula_Serie> buscarPeliculasPorRangoDeFecha(LocalDate desde, LocalDate hasta);

    List<Pelicula_Serie> buscarPeliculasPorRangoDeCalificacion(double desde, double hasta);

    Pelicula_Serie buscarPeliculaPorId(Long id);

    List<Pelicula_Serie> buscarPeliculaPorTitulo(String titulo);

    Pelicula_Serie altaPelicula(Pelicula_Serie peliculaNew);

    Pelicula_Serie updatePelicula(Pelicula_Serie pliculaAModificar, Pelicula_Serie peliculaNew);
}
