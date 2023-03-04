package com.besysoft.besysoftejercitacion1.service.implementations.database;

import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import com.besysoft.besysoftejercitacion1.dominio.entity.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.excepciones.IdInexistenteException;
import com.besysoft.besysoftejercitacion1.excepciones.PeliculaExistenteException;
import com.besysoft.besysoftejercitacion1.repositories.database.GeneroRepository;
import com.besysoft.besysoftejercitacion1.repositories.database.PeliculaRepository;
import com.besysoft.besysoftejercitacion1.repositories.database.PersonajeRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.PeliculaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "database")
@Slf4j
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
        log.info("Busqueda por rango de fecha" + "Desde: " + desde + "Hasta: " + hasta);
        return this.peliculaRepository.findByFechaCreacionBetween(desde, hasta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pelicula_Serie> buscarPeliculasPorRangoDeCalificacion(double desde, double hasta) {
        log.info("Busqueda por rango de calificacion" + "Desde: " + desde + "Hasta: " + hasta);
        return this.peliculaRepository.findByCalificacionBetween(desde, hasta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pelicula_Serie> buscarPeliculasPorTituloOrGenero(String titulo, String genero) throws IdInexistenteException {
        Genero generoEncontrado = this.generoRepository.findByNombre(genero);
        //valido que la busqueda sea por genero ya q si es por titulo va a devolver null el generoEncontrado y estaria mal el error "El genero no existe".
        if (generoEncontrado == null && !genero.equalsIgnoreCase("")) {
            throw new IdInexistenteException("El genero no existe");
        }
        return this.peliculaRepository.findByTituloOrGenero(titulo, generoEncontrado);
    }

    @Override
    @Transactional(readOnly = false)
    public Pelicula_Serie altaPelicula(Pelicula_Serie peliculaNew) throws PeliculaExistenteException, IdInexistenteException {
        this.validacionPelicula(peliculaNew, peliculaNew.getId());
        return this.peliculaRepository.save(peliculaNew);
    }

    @Override
    @Transactional(readOnly = false)
    public Pelicula_Serie updatePelicula(Pelicula_Serie peliculaNew, Long id) throws PeliculaExistenteException, IdInexistenteException {
        Pelicula_Serie peliculaEncontrada = this.peliculaRepository.findById(id).orElse(null);
        if (peliculaEncontrada != null) {
            this.validacionPelicula(peliculaNew, id);
            peliculaEncontrada.setTitulo(peliculaNew.getTitulo());
            peliculaEncontrada.setFechaCreacion(peliculaNew.getFechaCreacion());
            peliculaEncontrada.setCalificacion(peliculaNew.getCalificacion());
            peliculaEncontrada.setGenero(peliculaNew.getGenero());
            //para reutilizar la validacion de unique
            return this.peliculaRepository.save(peliculaEncontrada);
        }
        throw new IdInexistenteException("La pelicula que intenta modificar no existe");
    }

    public void validacionPelicula(Pelicula_Serie peliculaNew, Long id) throws PeliculaExistenteException, IdInexistenteException {
        Pelicula_Serie pelicula_serie = this.peliculaRepository.findByTitulo(peliculaNew.getTitulo());

        if (pelicula_serie != null) {
            if (!Objects.equals(pelicula_serie.getId(), id))
                throw new PeliculaExistenteException("La pelicula ya existe");
        }
        if (this.generoRepository.findById(peliculaNew.getGenero().getId()).isEmpty()) {
            throw new IdInexistenteException("El genero que intenta asignar a la pelicula no existe");
        }
    }
}
