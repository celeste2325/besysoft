package com.besysoft.besysoftejercitacion1.service.implementations.database;

import com.besysoft.besysoftejercitacion1.datos.DatosDummy;
import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import com.besysoft.besysoftejercitacion1.excepciones.GeneroExistenteException;
import com.besysoft.besysoftejercitacion1.excepciones.IdInexistenteException;
import com.besysoft.besysoftejercitacion1.repositories.database.GeneroRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.GeneroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class GeneroServiceDataBaseImplTest {
    @Autowired
    private GeneroService service;
    @MockBean
    private GeneroRepository repository;

    private DatosDummy datos;

    @BeforeEach
    void setUp() {
        datos = new DatosDummy();
    }

    @Test
    void altaGenero() throws GeneroExistenteException {
        //GIVEN
        //WHEN
        service.altaGenero(datos.getDramaMock());
        //THEN
        ArgumentCaptor<Genero> generoArgumentCaptor = ArgumentCaptor.forClass(Genero.class);

        //que paso por el metodo y que guardo un obj de tipo Genero
        verify(repository).save(generoArgumentCaptor.capture());
        Genero generoArgumentCaptorValue = generoArgumentCaptor.getValue();
        //y que lo que se guardo es igual al objeto que le pase por argumento
        assertThat(generoArgumentCaptorValue).isEqualTo(datos.getDramaMock());
    }

    @Test
    void altaGeneroWhitErrorGeneroExistente() {
        //GIVEN
        when(repository.findByNombre(any())).thenReturn(datos.getAveturaMock());

        //THEN
        assertThatThrownBy(() -> service.altaGenero(datos.getAventura2Mock()))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Ya existe un genero con mismo nombre");
    }

    @Test
    void updateGenero() throws GeneroExistenteException, IdInexistenteException {
        //GIVEN
        when(repository.findById(any())).thenReturn(Optional.of(datos.getAventura2Mock()));
        Genero generoNew = new Genero(6L, "aventura2Modificado");

        service.updateGenero(generoNew, generoNew.getId());
        //THEN
        ArgumentCaptor<Genero> generoArgumentCaptor = ArgumentCaptor.forClass(Genero.class);

        //que paso por el metodo y que guardo un obj de tipo Genero
        verify(repository).save(generoArgumentCaptor.capture());
        Genero generoArgumentCaptorValue = generoArgumentCaptor.getValue();
        //y que lo que se guardo es igual al objeto que le pase por argumento
        assertThat(generoArgumentCaptorValue.getNombre()).isEqualTo("aventura2Modificado");
    }

    @Test
    void updateGeneroWhitErrorGeneroInexistente() {
        //GIVEN
        when(repository.findById(any())).thenReturn(Optional.empty());

        //THEN
        assertThatThrownBy(() -> service.updateGenero(datos.getAventura2Mock(), datos.getAventura2Mock().getId()))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("El genero que intenta modificar no existe");
    }
}
