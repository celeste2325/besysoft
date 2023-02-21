package com.besysoft.besysoftejercitacion1.service.implementations;

import com.besysoft.besysoftejercitacion1.dominio.Genero;
import com.besysoft.besysoftejercitacion1.repositories.memory.GeneroRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.GeneroService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.CampoNombreEsObligatorioException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.GeneroInexistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.YaExisteGeneroConMismoNombreException;
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
    public Genero altaGenero(Genero newGenero) throws YaExisteGeneroConMismoNombreException, CampoNombreEsObligatorioException {
        //campo nombre es obligatorio
        if (newGenero.getNombre() != null) {
            if (this.generoRepository.buscarGeneroPorNombre(newGenero.getNombre()) == null) {
                return this.generoRepository.altaGenero(newGenero);
            } else {
                throw new YaExisteGeneroConMismoNombreException("Ya existe una genero con mismo nombre");
            }
        }
        throw new CampoNombreEsObligatorioException("El campo nombre es obligatorio");
    }

    @Override
    public Genero updateGenero(Genero newGenero, Long id) throws YaExisteGeneroConMismoNombreException, GeneroInexistenteException {
        Genero generoEncontradoById = this.generoRepository.buscarGeneroPorId(id);
        Genero generoEncontradoByNombre = this.generoRepository.buscarGeneroPorNombre(newGenero.getNombre());

        if (generoEncontradoById != null) {
            if (generoEncontradoByNombre != null && !Objects.equals(generoEncontradoByNombre.getId(), id)) {
                throw new YaExisteGeneroConMismoNombreException("Ya existe un genero con mismo nombre");
            }
            return this.generoRepository.updateGenero(generoEncontradoById, newGenero);
        }
        throw new GeneroInexistenteException("El genero que intenta modificar no existe");
    }
}
