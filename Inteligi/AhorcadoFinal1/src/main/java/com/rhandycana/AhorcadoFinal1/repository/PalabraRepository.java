package com.rhandycana.AhorcadoFinal1.repository;

import com.rhandycana.AhorcadoFinal1.model.Palabra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PalabraRepository extends JpaRepository<Palabra, Integer> {
    boolean existsByPalabraAndCategoria(String palabra, String categoria);
}