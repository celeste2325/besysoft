package com.besysoft.besysoftejercitacion1.service.interfaces;

import com.besysoft.besysoftejercitacion1.dominio.entity.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.IdInexistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PeliculaExistenteException;

import java.time.LocalDate;
import java.util.List;

public interface PeliculaService {
    List<Pelicula_Serie> obtenerTodos();

    List<Pelicula_Serie> buscarPeliculasPorRangoDeFecha(LocalDate desde, LocalDate hasta);

    List<Pelicula_Serie> buscarPeliculasPorRangoDeCalificacion(double desde, double hasta);

    List<Pelicula_Serie> buscarPeliculasPorTituloOrGenero(String titulo, String genero) throws IdInexistenteException;

    Pelicula_Serie altaPelicula(Pelicula_Serie peliculaNew) throws PeliculaExistenteException, IdInexistenteException;

    Pelicula_Serie updatePelicula(Pelicula_Serie peliculaNew, Long id) throws PeliculaExistenteException, IdInexistenteException;
}
