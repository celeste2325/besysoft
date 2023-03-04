package com.besysoft.besysoftejercitacion1.service.implementations.database;

import com.besysoft.besysoftejercitacion1.datos.DatosDummy;
import com.besysoft.besysoftejercitacion1.dominio.entity.Pelicula_Serie;
import com.besysoft.besysoftejercitacion1.repositories.database.GeneroRepository;
import com.besysoft.besysoftejercitacion1.repositories.database.PeliculaRepository;
import com.besysoft.besysoftejercitacion1.repositories.database.PersonajeRepository;
import com.besysoft.besysoftejercitacion1.service.interfaces.PeliculaService;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.IdInexistenteException;
import com.besysoft.besysoftejercitacion1.utilidades.exceptions.PeliculaExistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


//para trabajar los test con spring boot
@SpringBootTest
class PeliculaServiceDataBaseImplTest {
    //mock con spring
    @MockBean
    GeneroRepository generoRepository;
    @MockBean
    PersonajeRepository personajeRepository;
    @MockBean
    private PeliculaRepository peliculaRepository;
    @Autowired
    private PeliculaService service;
    private DatosDummy datos;

    @BeforeEach
    void setUp() {
        datos = new DatosDummy();
    }

    @Test
    void obtenerTodos() {
        //GIVEN
        when(this.peliculaRepository.findAll()).thenReturn(datos.getPeliculas_series());
        //WHEN
        List<Pelicula_Serie> peliculas = service.obtenerTodos();

        //THEN
        assertThat(peliculas.size()).isEqualTo(3);
        verify(peliculaRepository).findAll();
    }

    @Test
    void buscarPeliculasPorRangoDeFecha() {
        //GIVEN
        when(this.peliculaRepository.findByFechaCreacionBetween(any(), any())).thenReturn(DatosDummy.getPeliculasPorFiltro());
        //WHEN
        List<Pelicula_Serie> peliculas = service.buscarPeliculasPorRangoDeFecha(LocalDate.of(2010, 04, 17), LocalDate.of(2011, 04, 17));

        //THEN
        assertThat(peliculas.size()).isEqualTo(2);
        verify(peliculaRepository).findByFechaCreacionBetween(LocalDate.of(2010, 04, 17), LocalDate.of(2011, 04, 17));
    }

    @Test
    void buscarPeliculasPorRangoDeCalificacion() {
        //GIVEN
        when(this.peliculaRepository.findByCalificacionBetween(anyDouble(), anyDouble())).thenReturn(DatosDummy.getPeliculasPorFiltro());
        //WHEN
        List<Pelicula_Serie> peliculas = service.buscarPeliculasPorRangoDeCalificacion(2.0, 5);

        //THEN
        assertThat(peliculas.size()).isEqualTo(2);
        verify(peliculaRepository).findByCalificacionBetween(2.0, 5);
    }

    @Test
    void buscarPeliculasPorTituloOrGenero() throws IdInexistenteException {
        //GIVEN
        when(this.peliculaRepository.findByTituloOrGenero(any(), any())).thenReturn(DatosDummy.getPeliculasPorFiltro());
        //WHEN
        List<Pelicula_Serie> peliculas = service.buscarPeliculasPorTituloOrGenero("avatar2", "");

        //THEN
        assertThat(peliculas.size()).isEqualTo(2);
        verify(peliculaRepository).findByTituloOrGenero("avatar2", null);

    }

    @Test
    void buscarPeliculasPorTituloOrGeneroWithErrorGeneroInexistente() {
        //GIVEN
        Pelicula_Serie pelicula_serie = datos.getPeliculaMock();

        given(generoRepository.findById(any())).willReturn(Optional.empty());

        //THEN

        assertThatThrownBy(() -> service.buscarPeliculasPorTituloOrGenero("", "ciencia ficcion"))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("El genero no existe");
    }

    @Test
    void altaPelicula() throws IdInexistenteException, PeliculaExistenteException {
        //GIVEN
        when(generoRepository.findById(any())).thenReturn(Optional.of(datos.getAventura2Mock()));
        //WHEN
        service.altaPelicula(datos.getPeliculaMock());
        //THEN
        ArgumentCaptor<Pelicula_Serie> pelicula_serieArgumentCaptor = ArgumentCaptor.forClass(Pelicula_Serie.class);
        verify(peliculaRepository).save(pelicula_serieArgumentCaptor.capture());
        Pelicula_Serie pelicula_serieCaptor = pelicula_serieArgumentCaptor.getValue();

        assertThat(pelicula_serieCaptor).isEqualTo(datos.getPeliculaMock());
    }

    @Test
    void altaPeliculaWithErrorPeliculaExistente() {
        //GIVEN
        Pelicula_Serie pelicula_serie = datos.getPeliculaMock();
        when(generoRepository.findById(any())).thenReturn(Optional.of(datos.getAventura2Mock()));
        given(peliculaRepository.findByTitulo(pelicula_serie.getTitulo())).willReturn(pelicula_serie);

        //THEN

        assertThatThrownBy(() -> service.altaPelicula(pelicula_serie))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("La pelicula ya existe");
    }

    @Test
    void altaPeliculaWithErrorGeneroInexistente() {
        //GIVEN
        Pelicula_Serie pelicula_serie = datos.getPeliculaMock();

        given(generoRepository.findById(pelicula_serie.getGenero().getId())).willReturn(Optional.empty());

        //THEN

        assertThatThrownBy(() -> service.altaPelicula(pelicula_serie))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("El genero que intenta asignar a la pelicula no existe");
    }

    @Test
    void updatePelicula() throws IdInexistenteException, PeliculaExistenteException {
        //GIVEN
        when(peliculaRepository.findById(any())).thenReturn(Optional.of(datos.getPeliculaMock()));
        when(generoRepository.findById(any())).thenReturn(Optional.of(datos.getAventura2Mock()));
        service.updatePelicula(datos.getPeliculaUpdateMock(), datos.getPeliculaUpdateMock().getId());

        //THEN
        ArgumentCaptor<Pelicula_Serie> pelicula_serieArgumentCaptor = ArgumentCaptor.forClass(Pelicula_Serie.class);

        //que paso por el metodo y que guardo un obj de tipo Genero
        verify(peliculaRepository).save(pelicula_serieArgumentCaptor.capture());
        Pelicula_Serie pelicula_serieArgumentCaptorValue = pelicula_serieArgumentCaptor.getValue();
        //y que lo que se guardo es igual al objeto que le pase por argumento
        assertThat(pelicula_serieArgumentCaptorValue).isEqualTo(datos.getPeliculaMock());

    }

    @Test
    void updatePeliculaWhitErrorPeliculaInexistente() {
        //GIVEN
        when(this.peliculaRepository.findById(any())).thenReturn(Optional.empty());

        //THEN
        assertThatThrownBy(() -> service.updatePelicula(datos.getPeliculaUpdateMock(), 5L))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("La pelicula que intenta modificar no existe");
    }
}
