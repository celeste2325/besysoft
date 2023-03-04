package com.besysoft.besysoftejercitacion1.repositories.database;

import com.besysoft.besysoftejercitacion1.dominio.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GeneroRepository extends JpaRepository<Genero, Long> {
    @Query("select s from Genero s where s.nombre = ?1")
    Genero findByNombre(String nombre);
}
