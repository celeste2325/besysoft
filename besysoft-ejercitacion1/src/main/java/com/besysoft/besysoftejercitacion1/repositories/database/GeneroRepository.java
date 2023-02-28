package com.besysoft.besysoftejercitacion1.repositories.database;

import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepository extends JpaRepository<Genero, Long> {
    Genero findByNombre(String nombre);
}
