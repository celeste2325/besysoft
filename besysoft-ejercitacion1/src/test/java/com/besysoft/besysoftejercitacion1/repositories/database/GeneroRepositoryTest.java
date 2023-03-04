package com.besysoft.besysoftejercitacion1.repositories.database;

import com.besysoft.besysoftejercitacion1.datos.DatosDummy;
import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GeneroRepositoryTest {
    @Autowired
    private GeneroRepository repository;
    private DatosDummy datos;

    @BeforeEach
    void setUp() {
        datos = new DatosDummy();
        repository.saveAll(datos.getGeneros());
    }

    @Test
    void findByNombre() {
        //GIVEN --> contexto
        String test = "aventura";

        // WHEN --> que queremos probar
        Genero genero = this.repository.findByNombre(test);
        //THEN
        assertThat(genero != null).isTrue();
        assertThat(genero.getNombre()).isEqualTo(test);
    }
}
