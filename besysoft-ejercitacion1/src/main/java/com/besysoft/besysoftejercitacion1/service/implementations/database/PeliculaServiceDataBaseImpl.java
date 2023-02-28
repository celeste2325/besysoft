package com.besysoft.besysoftejercitacion1.service.implementations.database;

import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import com.besysoft.besysoftejercitacion1.dominio.entity.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.repositories.database.GeneroRepository;
import com.besysoft.besysoftejercitacion1.repositories.database.PeliculaRepository;
import com.besysoft.besysoftejercitacion1.repositories.database.PersonajeRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.PeliculaService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.GeneroInexistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.IdInexistente;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PeliculaExistenteConMismoTituloException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;


@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "database")
public class PeliculaServiceDataBaseImpl implements PeliculaService {
    private final PeliculaRepository peliculaRepository;
    private final GeneroRepository generoRepository;

    private final PersonajeRepository personajeRepository;

    public PeliculaServiceDataBaseImpl(PeliculaRepository peliculaRepository, GeneroRepository generoRepository, PersonajeRepository personajeRepository) {
        this.peliculaRepository = peliculaRepository;
        this.generoRepository = generoRepository;
        this.personajeRepository = personajeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pelicula_Serie> obtenerTodos() {
        return this.peliculaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pelicula_Serie> buscarPeliculasPorRangoDeFecha(LocalDate desde, LocalDate hasta) {
        return this.peliculaRepository.findByFechaCreacionBetween(desde, hasta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pelicula_Serie> buscarPeliculasPorRangoDeCalificacion(double desde, double hasta) {
        return this.peliculaRepository.findByCalificacionBetween(desde, hasta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pelicula_Serie> buscarPeliculasPorTituloOrGenero(String titulo, String genero) throws GeneroInexistenteException {
        Genero generoEncontrado = this.generoRepository.findByNombre(genero);
        //valido que la busqueda sea por genero ya q si es por titulo va a devolver null el generoEncontrado y estaria mal el error "El genero no existe".
        if (generoEncontrado == null && !genero.equalsIgnoreCase("")) {
            throw new GeneroInexistenteException("El genero no existe");
        }
        return this.peliculaRepository.findByTituloOrGenero(titulo, generoEncontrado);
    }

    @Override
    @Transactional(readOnly = false)
    public Pelicula_Serie altaPelicula(Pelicula_Serie peliculaNew) throws PeliculaExistenteConMismoTituloException, IdInexistente {
        try {
            return this.peliculaRepository.save(peliculaNew);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {

            if (e.getCause() instanceof ConstraintViolationException) {
                //verificacion de unicidad
                if (e.getCause().getCause().getMessage().contains("Violación de indice de Unicidad ó Clave primaria")) {
                    throw new PeliculaExistenteConMismoTituloException("Ya existe una pelicula con mismo titulo");
                    //caso en que el id del genero de la pelicula a crear no exista
                } else if (e.getCause().getCause().getMessage().contains("Violación de una restricción de Integridad Referencial")) {
                    throw new IdInexistente(format("El id %d, correspondiente al genero no existe.", peliculaNew.getGenero().getId()));
                }
            }
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public Pelicula_Serie updatePelicula(Pelicula_Serie peliculaNew, Long id) throws PeliculaExistenteConMismoTituloException, IdInexistente {
        Pelicula_Serie peliculaEncontrada = this.peliculaRepository.findById(id).orElse(null);
        if (peliculaEncontrada != null) {
            peliculaEncontrada.setTitulo(peliculaNew.getTitulo());
            peliculaEncontrada.setFechaCreacion(peliculaNew.getFechaCreacion());
            peliculaEncontrada.setCalificacion(peliculaNew.getCalificacion());
            peliculaEncontrada.setGenero(peliculaNew.getGenero());
            //para reutilizar la validacion de unique
            return this.altaPelicula(peliculaEncontrada);
        }
        throw new IdInexistente("La pelicula que intenta modificar no existe");
    }
}
