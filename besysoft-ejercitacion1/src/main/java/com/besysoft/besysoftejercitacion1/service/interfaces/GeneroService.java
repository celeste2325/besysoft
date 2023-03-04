package com.besysoft.besysoftejercitacion1.service.interfaces;

import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.GeneroExistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.IdInexistenteException;

public interface GeneroService {
    Genero altaGenero(Genero newGenero) throws GeneroExistenteException;

    Genero updateGenero(Genero newGenero, Long id) throws GeneroExistenteException, IdInexistenteException;
}
