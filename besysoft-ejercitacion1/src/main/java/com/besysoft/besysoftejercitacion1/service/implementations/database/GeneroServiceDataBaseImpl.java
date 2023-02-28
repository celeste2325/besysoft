package com.besysoft.besysoftejercitacion1.service.implementations.database;

import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import com.besysoft.besysoftejercitacion1.repositories.database.GeneroRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.GeneroService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.GeneroInexistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.YaExisteGeneroConMismoNombreException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "database")
public class GeneroServiceDataBaseImpl implements GeneroService {
    private final GeneroRepository generoRepository;

    public GeneroServiceDataBaseImpl(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public Genero altaGenero(Genero newGenero) throws YaExisteGeneroConMismoNombreException {
        try {
            return this.generoRepository.save(newGenero);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new YaExisteGeneroConMismoNombreException("Ya existe un genero con mismo nombre");
            } else {
                throw e;
            }
        }
    }

    @Override
    @Transactional(readOnly = false)
    public Genero updateGenero(Genero newGenero, Long id) throws GeneroInexistenteException, YaExisteGeneroConMismoNombreException {
        Genero generoEncontradoById = this.generoRepository.findById(id).orElse(null);

        if (generoEncontradoById != null) {
            generoEncontradoById.setNombre(newGenero.getNombre());
            //para reutilizar la validacion de unique
            return this.altaGenero(generoEncontradoById);
        }
        throw new GeneroInexistenteException("El genero que intenta modificar no existe");
    }
}
