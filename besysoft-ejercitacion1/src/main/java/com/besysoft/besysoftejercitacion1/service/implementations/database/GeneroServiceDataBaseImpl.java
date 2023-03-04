package com.besysoft.besysoftejercitacion1.service.implementations.database;

import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import com.besysoft.besysoftejercitacion1.repositories.database.GeneroRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.GeneroService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.GeneroInexistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.YaExisteGeneroConMismoNombreException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

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
        Optional<Genero> genero = this.generoRepository.findById(newGenero.getId());
        if (genero.isPresent()) {
            if (!Objects.equals(genero.get().getId(), newGenero.getId()))
                throw new YaExisteGeneroConMismoNombreException("Ya existe un genero con mismo nombre");
        }
        return this.generoRepository.save(newGenero);
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
