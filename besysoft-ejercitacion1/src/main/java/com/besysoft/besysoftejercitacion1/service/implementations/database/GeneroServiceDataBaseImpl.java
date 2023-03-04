package com.besysoft.besysoftejercitacion1.service.implementations.database;

import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import com.besysoft.besysoftejercitacion1.excepciones.GeneroExistenteException;
import com.besysoft.besysoftejercitacion1.excepciones.IdInexistenteException;
import com.besysoft.besysoftejercitacion1.repositories.database.GeneroRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.GeneroService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "database")
public class GeneroServiceDataBaseImpl implements GeneroService {
    private final GeneroRepository generoRepository;

    public GeneroServiceDataBaseImpl(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public Genero altaGenero(Genero newGenero) throws GeneroExistenteException {
        this.validacionIdGenero(newGenero, newGenero.getId());
        return this.generoRepository.save(newGenero);
    }

    @Override
    @Transactional(readOnly = false)
    public Genero updateGenero(Genero newGenero, Long id) throws IdInexistenteException, GeneroExistenteException {
        Genero generoEncontradoById = this.generoRepository.findById(id).orElse(null);

        if (generoEncontradoById != null) {
            this.validacionIdGenero(newGenero, id);
            generoEncontradoById.setNombre(newGenero.getNombre());
            return this.generoRepository.save(generoEncontradoById);
        }
        throw new IdInexistenteException("El id del genero que intenta modificar no existe");
    }

    //paso el id para poder usar el metodo tanto en el save como en el update
    public void validacionIdGenero(Genero newGenero, Long id) throws GeneroExistenteException {
        Genero genero = this.generoRepository.findByNombre(newGenero.getNombre());
        if (genero != null) {
            if (!Objects.equals(genero.getId(), id))
                throw new GeneroExistenteException("Ya existe un genero con mismo nombre");
        }
    }

}
