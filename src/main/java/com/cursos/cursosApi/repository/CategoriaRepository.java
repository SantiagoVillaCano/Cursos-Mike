package com.cursos.cursosApi.repository;

import com.cursos.cursosApi.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}