package com.besysoft.besysoftejercitacion1.repositories.database;

import com.besysoft.besysoftejercitacion1.datos.DatosDummy;
import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import com.besysoft.besysoftejercitacion1.dominio.entity.Pelicula_Serie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PeliculaRepositoryTest {
    @Autowired
    private PeliculaRepository peliculaRepository;
    private DatosDummy datos;

    @BeforeEach
    void setUp() {
        datos = new DatosDummy();
        peliculaRepository.saveAll(datos.getPeliculas_series());
    }

    @Test
    void findByTituloOrGenero() {
        //GIVEN --> contexto
        String titulo = "Matrix";
        Genero genero = new Genero(2L, "accion");

        // WHEN --> que queremos probar
        List<Pelicula_Serie> pelicula_series = this.peliculaRepository.findByTituloOrGenero("", genero);
        //THEN
        assertThat(pelicula_series != null).isTrue();
        pelicula_series.forEach(p -> assertThat(p.getGenero()).isEqualTo(genero));

    }

    @Test
    void findByFechaCreacionBetween() {
        //GIVEN --> contexto
        LocalDate desde = LocalDate.of(2011, 04, 17);
        LocalDate hasta = LocalDate.of(2011, 06, 17);

        // WHEN --> que queremos probar
        List<Pelicula_Serie> pelicula_series = this.peliculaRepository.findByFechaCreacionBetween(desde, hasta);
        //THEN
        assertThat(pelicula_series != null).isTrue();
        assertThat(pelicula_series.size()).isEqualTo(1);
        pelicula_series.forEach(p -> assertThat(p.getFechaCreacion()).isBetween(desde, hasta));
    }

    @Test
    void findByCalificacionBetween() {
        //GIVEN --> contexto
        double desde = 4.8;
        double hasta = 5.0;

        // WHEN --> que queremos probar
        List<Pelicula_Serie> pelicula_series = this.peliculaRepository.findByCalificacionBetween(desde, hasta);
        //THEN
        assertThat(pelicula_series != null).isTrue();
        assertThat(pelicula_series.size()).isEqualTo(2);
        pelicula_series.forEach(p -> assertThat(p.getCalificacion()).isBetween(desde, hasta));
    }

    @Test
    void findByTitulo() {
        //GIVEN --> contexto
        String titulo = "Matrix";

        // WHEN --> que queremos probar
        Pelicula_Serie pelicula_series = this.peliculaRepository.findByTitulo(titulo);
        //THEN
        assertThat(pelicula_series != null).isTrue();
        assertThat(pelicula_series.getTitulo()).isEqualTo(titulo);
    }
}
