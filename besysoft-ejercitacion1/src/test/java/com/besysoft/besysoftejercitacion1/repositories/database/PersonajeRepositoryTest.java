package com.besysoft.besysoftejercitacion1.repositories.database;

import com.besysoft.besysoftejercitacion1.datos.DatosDummy;
import com.besysoft.besysoftejercitacion1.dominio.entity.Personaje;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PersonajeRepositoryTest {
    @Autowired
    private PersonajeRepository personajeRepository;
    private DatosDummy datos;

    @BeforeEach
    void setUp() {
        datos = new DatosDummy();
        personajeRepository.saveAll(datos.getPersonajes());
    }

    @AfterEach
    void tearDown() {
        personajeRepository.deleteAll();
    }


    @Test
    void findByEdadBetween() {
        //GIVEN --> contexto
        int desde = 58;
        int hasta = 61;

        // WHEN --> que queremos probar
        List<Personaje> personajes = this.personajeRepository.findByEdadBetween(desde, hasta);
        //THEN
        assertThat(personajes != null).isTrue();
        personajes.forEach(p -> assertThat(p.getEdad()).isBetween(desde, hasta));

    }

    @Test
    void findByNombreOrEdad() {
        //GIVEN --> contexto
        int edad = 58;

        // WHEN --> que queremos probar
        List<Personaje> personajes = this.personajeRepository.findByNombreOrEdad("", edad);
        //THEN
        assertThat(personajes != null).isTrue();
        personajes.forEach(p -> assertThat(p.getEdad()).isEqualTo(edad));

    }

    @Test
    void findByNombre() {
        //GIVEN --> contexto
        String nombre = "Keanu Charles Reeves";

        // WHEN --> que queremos probar
        Optional<Personaje> personaje = this.personajeRepository.findByNombre(nombre);
        //THEN
        assertThat(personaje.isPresent()).isTrue();
        assertThat(personaje.get().getNombre()).isEqualTo(nombre);
    }
}
