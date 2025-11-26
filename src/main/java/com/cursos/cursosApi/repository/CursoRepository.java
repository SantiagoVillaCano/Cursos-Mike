package com.cursos.cursosApi.repository;

import com.cursos.cursosApi.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}