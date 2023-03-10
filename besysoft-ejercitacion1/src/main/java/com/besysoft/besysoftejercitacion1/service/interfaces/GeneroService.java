package com.besysoft.besysoftejercitacion1.service.interfaces;

import com.besysoft.besysoftejercitacion1.dominio.Genero;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.CampoNombreEsObligatorioException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.GeneroInexistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.YaExisteGeneroConMismoNombreException;

public interface GeneroService {
    Genero altaGenero(Genero newGenero) throws YaExisteGeneroConMismoNombreException, CampoNombreEsObligatorioException;

    Genero updateGenero(Genero newGenero, Long id) throws YaExisteGeneroConMismoNombreException, GeneroInexistenteException;
}
