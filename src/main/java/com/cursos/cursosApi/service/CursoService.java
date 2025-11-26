package com.cursos.cursosApi.service;

import com.cursos.cursosApi.entity.Curso;
import com.cursos.cursosApi.repository.CursoRepository;
import com.cursos.cursosApi.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {
    @Autowired
    private CursoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Curso create(Curso curso) {
        if (curso.getCategoria() == null || curso.getCategoria().getId() == null) {
            throw new IllegalArgumentException("Categoria requerida");
        }
        if (!categoriaRepository.existsById(curso.getCategoria().getId())) {
            throw new IllegalArgumentException("Categoria no existe");
        }
        return repository.save(curso);
    }

    public List<Curso> listAll() {
        return repository.findAll();
    }

    public Optional<Curso> findById(Long id) {
        return repository.findById(id);
    }

    public Curso update(Long id, Curso curso) {
        return repository.findById(id).map(existing -> {
            if (curso.getTitulo() != null) existing.setTitulo(curso.getTitulo());
            if (curso.getDescripcion() != null) existing.setDescripcion(curso.getDescripcion());
            if (curso.getCategoria() != null && curso.getCategoria().getId() != null) {
                categoriaRepository.findById(curso.getCategoria().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Categoria no existe"));
                existing.setCategoria(curso.getCategoria());
            }
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Curso no encontrado"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}