package com.besysoft.besysoftejercitacion1.service.implementations.database;

import com.besysoft.besysoftejercitacion1.datos.DatosDummy;
import com.besysoft.besysoftejercitacion1.dominio.entity.Personaje;
import com.besysoft.besysoftejercitacion1.excepciones.ErrorDeBusquedaException;
import com.besysoft.besysoftejercitacion1.excepciones.IdInexistenteException;
import com.besysoft.besysoftejercitacion1.excepciones.PersonajeExisteException;
import com.besysoft.besysoftejercitacion1.repositories.database.PersonajeRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.PersonajeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PersonajeServiceDataBaseImplTest {

    @MockBean
    private PersonajeRepository personajeRepository;

    @Autowired
    private PersonajeService personajeService;
    private DatosDummy datos;

    @BeforeEach
    void setUp() {
        this.datos = new DatosDummy();
    }

    @Test
    void obtenerTodos() {
        //GIVEN
        when(this.personajeRepository.findAll()).thenReturn(datos.getPersonajes());
        //WHEN
        List<Personaje> personajes = personajeService.obtenerTodos();

        //THEN
        assertThat(personajes.size()).isEqualTo(2);
        verify(personajeRepository).findAll();
    }

    @Test
    void buscarPersonajesPorNombreOrEdad() throws ErrorDeBusquedaException {
        when(this.personajeRepository.findByNombreOrEdad(any(), anyInt())).thenReturn(DatosDummy.getPersonajesPorFiltro());
        //WHEN
        List<Personaje> personajes = personajeService.buscarPersonajesPorNombreOrEdad("", 49);

        //THEN
        assertThat(personajes.size()).isEqualTo(2);
        verify(personajeRepository).findByNombreOrEdad("", 49);
    }

    @Test
    void buscarPersonajesPorRangoDeEdad() {
        when(this.personajeRepository.findByEdadBetween(anyInt(), anyInt())).thenReturn(DatosDummy.getPersonajesPorFiltro());
        //WHEN
        List<Personaje> personajes = personajeService.buscarPersonajesPorRangoDeEdad(40, 49);

        //THEN
        assertThat(personajes.size()).isEqualTo(2);
        verify(personajeRepository).findByEdadBetween(40, 49);
    }

    @Test
    void altaPersonaje() throws PersonajeExisteException {
        //GIVEN
        when(personajeRepository.findByNombre(any())).thenReturn(Optional.of(DatosDummy.getPersonaje1()));
        //WHEN
        personajeService.altaPersonaje(DatosDummy.getPersonaje1());
        //THEN
        ArgumentCaptor<Personaje> personajeArgumentCaptor = ArgumentCaptor.forClass(Personaje.class);
        verify(personajeRepository).save(personajeArgumentCaptor.capture());
        Personaje personajeArgumentCaptorValue = personajeArgumentCaptor.getValue();

        assertThat(personajeArgumentCaptorValue).isEqualTo(DatosDummy.getPersonaje1());
    }

    @Test
    void altaPersonajeWithErrorPersonajeExistente() {
        //GIVEN
        when(personajeRepository.findByNombre(any())).thenReturn(Optional.of(DatosDummy.getPersonaje1()));
        //THEN
        assertThatThrownBy(() -> personajeService.altaPersonaje(DatosDummy.getPersonaje2()))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Ya existe un personaje con misma nombre");
    }

    @Test
    void updatePersonaje() throws PersonajeExisteException, IdInexistenteException {
        //GIVEN
        when(personajeRepository.findById(any())).thenReturn(Optional.of(DatosDummy.getPersonaje1()));
        personajeService.updatePersonaje(DatosDummy.getPersonaje1Update(), DatosDummy.getPersonaje1Update().getId());

        //THEN
        ArgumentCaptor<Personaje> personajeArgumentCaptor = ArgumentCaptor.forClass(Personaje.class);

        //que paso por el metodo y que guardo un obj de tipo Genero
        verify(personajeRepository).save(personajeArgumentCaptor.capture());
        Personaje personajeArgumentCaptorValue = personajeArgumentCaptor.getValue();
        //y que lo que se guardo es igual al objeto que le pase por argumento
        assertThat(personajeArgumentCaptorValue).isEqualTo(DatosDummy.getPersonaje1Update());

    }

    @Test
    void updatePersonajeWithErrorPersonajeInexistente() {
        //GIVEN
        when(personajeRepository.findById(any())).thenReturn(Optional.empty());
        //THEN
        assertThatThrownBy(() -> personajeService.updatePersonaje(DatosDummy.getPersonaje1Update(), DatosDummy.getPersonaje1Update().getId()))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("El id ingresado no existe");

    }
}
