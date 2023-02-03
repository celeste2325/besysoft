package com.besysoft.besysoftejercitacion1.service.implementations;

import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.repositories.PeliculaRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.PeliculaService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.ElCampoTituloEsObligatorioException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.IdInexistente;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PeliculaExistenteConMismoTituloException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.RangoDeCalificacionExcedidoException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Service
public class PeliculaServiceImpl implements PeliculaService {
    private final PeliculaRepository peliculaRepository;

    public PeliculaServiceImpl(PeliculaRepository repository) {
        this.peliculaRepository = repository;
    }

    @Override
    public List<Pelicula_Serie> obtenerTodos() {
        return this.peliculaRepository.obtenerTodos();
    }

    @Override
    public List<Pelicula_Serie> buscarPeliculasPorRangoDeFecha(LocalDate desde, LocalDate hasta) {
        return this.peliculaRepository.buscarPeliculasPorRangoDeFecha(desde, hasta);
    }

    @Override
    public List<Pelicula_Serie> buscarPeliculasPorRangoDeCalificacion(double desde, double hasta) {
        return this.peliculaRepository.buscarPeliculasPorRangoDeCalificacion(desde, hasta);
    }

    @Override
    public Pelicula_Serie altaPelicula(Pelicula_Serie peliculaNew) throws PeliculaExistenteConMismoTituloException, ElCampoTituloEsObligatorioException, RangoDeCalificacionExcedidoException {

        //se asumen obligatorios el campos titulo.
        if (peliculaNew.getTitulo() != null) {
            //la pelicula existe
            if (this.peliculaRepository.buscarPeliculaPorTitulo(peliculaNew) != null) {
                throw new PeliculaExistenteConMismoTituloException("Ya existe una pelicula con mismo titulo");
            }
            if (peliculaNew.getCalificacion() != 0.0 && (peliculaNew.getCalificacion() > 5 || peliculaNew.getCalificacion() < 1)) {
                throw new RangoDeCalificacionExcedidoException("El rango permitido de calificación es de 1 a 5");
            }
            return this.peliculaRepository.altaPelicula(peliculaNew);
        } else {
            throw new ElCampoTituloEsObligatorioException("El campo titulo es obligatorio");
        }
    }

    @Override
    public Pelicula_Serie updatePelicula(Pelicula_Serie peliculaNew, Long id) throws PeliculaExistenteConMismoTituloException, IdInexistente, RangoDeCalificacionExcedidoException {
        Pelicula_Serie peliculaEncontradaPorId = this.peliculaRepository.buscarPeliculaPorId(id);
        Pelicula_Serie peliculaEncontradaPorTitulo = this.peliculaRepository.buscarPeliculaPorTitulo(peliculaNew);

        //existe para ser modificada
        if (peliculaEncontradaPorId != null) {
            //valida que al modificar la pelicula no se cambie el titulo por uno ya existente
            if (peliculaEncontradaPorTitulo != null && !Objects.equals(peliculaEncontradaPorTitulo.getId(), id)) {
                throw new PeliculaExistenteConMismoTituloException((format("Ya existe una pelicula con mismo titulo: %s", peliculaNew.getTitulo())));
            }
            if (peliculaNew.getCalificacion() != 0.0 && (peliculaNew.getCalificacion() > 5 || peliculaNew.getCalificacion() < 1)) {
                throw new RangoDeCalificacionExcedidoException("El rango permitido de calificación es de 1 a 5");
            }
            return this.peliculaRepository.updatePelicula(peliculaEncontradaPorId, peliculaNew);
        }
        throw new IdInexistente((format("El ID %d ingresado no existe", id)));
    }
}
