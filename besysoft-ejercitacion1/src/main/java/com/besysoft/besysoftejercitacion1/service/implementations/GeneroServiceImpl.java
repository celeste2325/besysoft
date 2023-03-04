package com.besysoft.besysoftejercitacion1.service.implementations;

import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import com.besysoft.besysoftejercitacion1.repositories.memory.GeneroRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.GeneroService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.GeneroExistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.IdInexistenteException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "memory")
public class GeneroServiceImpl implements GeneroService {
    private final GeneroRepository generoRepository;

    public GeneroServiceImpl(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    @Override
    public Genero altaGenero(Genero newGenero) throws GeneroExistenteException {
        //campo nombre es obligatorio
        if (this.generoRepository.buscarGeneroPorNombre(newGenero.getNombre()) == null) {
            return this.generoRepository.altaGenero(newGenero);
        } else {
            throw new GeneroExistenteException("Ya existe una genero con mismo nombre");
        }
    }

    @Override
    public Genero updateGenero(Genero newGenero, Long id) throws GeneroExistenteException, IdInexistenteException {
        Genero generoEncontradoById = this.generoRepository.buscarGeneroPorId(id);
        Genero generoEncontradoByNombre = this.generoRepository.buscarGeneroPorNombre(newGenero.getNombre());

        if (generoEncontradoById != null) {
            if (generoEncontradoByNombre != null && !Objects.equals(generoEncontradoByNombre.getId(), id)) {
                throw new GeneroExistenteException("Ya existe un genero con mismo nombre");
            }
            return this.generoRepository.updateGenero(generoEncontradoById, newGenero);
        }
        throw new IdInexistenteException("El id del genero que intenta modificar no existe");
    }
}
