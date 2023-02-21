package com.besysoft.besysoftejercitacion1.service.implementations.database;

import com.besysoft.besysoftejercitacion1.dominio.Genero;
import com.besysoft.besysoftejercitacion1.dominio.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.repositories.database.GeneroRepository;
import com.besysoft.besysoftejercitacion1.repositories.database.PeliculaRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.PeliculaService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.BuscarPorGeneroOtituloException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.GeneroInexistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.IdInexistente;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PeliculaExistenteConMismoTituloException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "database")
public class PeliculaServiceDataBaseImpl implements PeliculaService {
    private final PeliculaRepository peliculaRepository;
    private final GeneroRepository generoRepository;

    public PeliculaServiceDataBaseImpl(PeliculaRepository peliculaRepository, GeneroRepository generoRepository) {
        this.peliculaRepository = peliculaRepository;
        this.generoRepository = generoRepository;
    }

    @Override
    public List<Pelicula_Serie> obtenerTodos() {
        return this.peliculaRepository.findAll();
    }

    @Override
    public List<Pelicula_Serie> buscarPeliculasPorRangoDeFecha(LocalDate desde, LocalDate hasta) {
        return this.peliculaRepository.findByFechaCreacionBetween(desde, hasta);
    }

    @Override
    public List<Pelicula_Serie> buscarPeliculasPorRangoDeCalificacion(double desde, double hasta) {
        return this.peliculaRepository.findByCalificacionBetween(desde, hasta);
    }

    @Override
    public List<Pelicula_Serie> buscarPeliculasPorTituloOrGenero(String titulo, String genero) throws BuscarPorGeneroOtituloException, GeneroInexistenteException {
        Genero generoEncontrado = this.generoRepository.findByNombre(genero);
        if (generoEncontrado == null && !genero.equalsIgnoreCase("")) {
            throw new GeneroInexistenteException("El genero no existe");
        }
        //no admite AND es or
        if (!genero.equalsIgnoreCase("") && !titulo.equalsIgnoreCase("")) {
            throw new BuscarPorGeneroOtituloException("Debe Buscar o por titulo o por Genero. No se admiten ambos");
        }
        return this.peliculaRepository.findByTituloOrGenero(titulo, generoEncontrado);
    }

    @Override
    public Pelicula_Serie altaPelicula(Pelicula_Serie peliculaNew) throws PeliculaExistenteConMismoTituloException {
        try {
            return this.peliculaRepository.save(peliculaNew);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new PeliculaExistenteConMismoTituloException("Ya existe una pelicula con mismo titulo");
        }
    }

    @Override
    public Pelicula_Serie updatePelicula(Pelicula_Serie peliculaNew, Long id) throws PeliculaExistenteConMismoTituloException, IdInexistente {
        Pelicula_Serie peliculaEncontrada = this.peliculaRepository.findById(id).orElse(null);
        if (peliculaEncontrada != null) {
            peliculaEncontrada.setTitulo(peliculaNew.getTitulo());
            peliculaEncontrada.setFechaCreacion(peliculaNew.getFechaCreacion());
            peliculaEncontrada.setCalificacion(peliculaNew.getCalificacion());
            peliculaEncontrada.setGenero(peliculaNew.getGenero());
            //editar lista de pesonajes asociados a la pelicula
            peliculaNew.getPersonajes().forEach(personaje ->
                    {
                        //encuentra aquellos personajes q no estan cargados a la pelicula
                        if (!peliculaEncontrada.getPersonajes().contains(personaje)) {

                            //si no esta cargado el personaje a la pelicula lo agrega
                            peliculaEncontrada.getPersonajes().add(personaje);
                        }
                    }
            );
            return this.altaPelicula(peliculaEncontrada);
        }
        throw new IdInexistente("La pelicula que intenta modificar no existe");
    }
}
