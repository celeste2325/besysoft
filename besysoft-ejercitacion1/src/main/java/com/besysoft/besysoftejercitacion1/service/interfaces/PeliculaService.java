package com.besysoft.besysoftejercitacion1.service.interfaces;

import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.ElCampoTituloEsObligatorioException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.IdInexistente;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PeliculaExistenteConMismoTituloException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.RangoDeCalificacionExcedidoException;

import java.time.LocalDate;
import java.util.List;

public interface PeliculaService {
    List<Pelicula_Serie> obtenerTodos();

    List<Pelicula_Serie> buscarPeliculasPorRangoDeFecha(LocalDate desde, LocalDate hasta);

    List<Pelicula_Serie> buscarPeliculasPorRangoDeCalificacion(double desde, double hasta);

    Pelicula_Serie altaPelicula(Pelicula_Serie peliculaNew) throws PeliculaExistenteConMismoTituloException, ElCampoTituloEsObligatorioException, RangoDeCalificacionExcedidoException;

    Pelicula_Serie updatePelicula(Pelicula_Serie peliculaNew, Long id) throws PeliculaExistenteConMismoTituloException, IdInexistente, RangoDeCalificacionExcedidoException;
}
