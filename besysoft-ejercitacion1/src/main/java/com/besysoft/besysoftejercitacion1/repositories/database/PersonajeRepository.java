package com.besysoft.besysoftejercitacion1.repositories.database;


import com.besysoft.besysoftejercitacion1.dominio.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonajeRepository extends JpaRepository<Personaje, Long> {

    List<Personaje> findByNombreOrEdad(String nombre, int edad);

    List<Personaje> findByEdadBetween(int desde, int hasta);
}
