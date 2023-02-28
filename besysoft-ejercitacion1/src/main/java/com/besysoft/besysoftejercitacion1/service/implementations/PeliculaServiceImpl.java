package com.besysoft.besysoftejercitacion1.service.implementations;

import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import com.besysoft.besysoftejercitacion1.dominio.entity.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.repositories.memory.GeneroRepository;
import com.besysoft.besysoftejercitacion1.repositories.memory.PeliculaRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.PeliculaService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.GeneroInexistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.IdInexistente;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PeliculaExistenteConMismoTituloException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "memory")
public class PeliculaServiceImpl implements PeliculaService {
    private final PeliculaRepository peliculaRepository;
    private final GeneroRepository generoRepository;

    public PeliculaServiceImpl(PeliculaRepository peliculaRepository, GeneroRepository generoRepository) {
        this.peliculaRepository = peliculaRepository;
        this.generoRepository = generoRepository;
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
    public List<Pelicula_Serie> buscarPeliculasPorTituloOrGenero(String titulo, String nombreGenero) throws GeneroInexistenteException {
        if (!titulo.equalsIgnoreCase("") && nombreGenero.equalsIgnoreCase("")) {
            return this.peliculaRepository.buscarPeliculaPorTitulo(titulo);

        }
        Genero generoEncontrado = this.generoRepository.buscarGeneroPorNombre(nombreGenero);
        if (generoEncontrado != null) {
            return generoEncontrado.getPeliculas_series();
        } else {
            throw new GeneroInexistenteException("No existe el genero : " + nombreGenero);
        }
    }

    @Override
    public Pelicula_Serie altaPelicula(Pelicula_Serie peliculaNew) throws PeliculaExistenteConMismoTituloException {

        //la pelicula existe
        if (this.peliculaRepository.buscarPeliculaPorTitulo(peliculaNew.getTitulo()) != null) {
            throw new PeliculaExistenteConMismoTituloException("Ya existe una pelicula con mismo titulo");
        }
        return this.peliculaRepository.altaPelicula(peliculaNew);

    }

    @Override
    public Pelicula_Serie updatePelicula(Pelicula_Serie peliculaNew, Long id) throws PeliculaExistenteConMismoTituloException, IdInexistente {
        Pelicula_Serie peliculaEncontradaPorId = this.peliculaRepository.buscarPeliculaPorId(id);
        Pelicula_Serie peliculaEncontradaPorTitulo = this.peliculaRepository.buscarPeliculaPorTitulo(peliculaNew.getTitulo()).stream().findAny().orElse(null);

        //existe para ser modificada
        if (peliculaEncontradaPorId != null) {
            //valida que al modificar la pelicula no se cambie el titulo por uno ya existente
            if (peliculaEncontradaPorTitulo != null && !Objects.equals(peliculaEncontradaPorTitulo.getId(), id)) {
                throw new PeliculaExistenteConMismoTituloException((format("Ya existe una pelicula con mismo titulo: %s", peliculaNew.getTitulo())));
            }
            return this.peliculaRepository.updatePelicula(peliculaEncontradaPorId, peliculaNew);
        }
        throw new IdInexistente((format("El ID %d ingresado no existe", id)));
    }
}
