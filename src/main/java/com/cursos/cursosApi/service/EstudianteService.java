package com.cursos.cursosApi.service;

import com.cursos.cursosApi.entity.Estudiante;
import com.cursos.cursosApi.entity.Curso;
import com.cursos.cursosApi.repository.EstudianteRepository;
import com.cursos.cursosApi.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EstudianteService {
    @Autowired
    private EstudianteRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    public Estudiante create(Estudiante estudiante) {
        if (estudiante.getNombre() == null || estudiante.getNombre().isEmpty()) {
            throw new IllegalArgumentException("Nombre requerido");
        }
        if (estudiante.getEmail() == null || estudiante.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email requerido");
        }
        if (repository.findByEmail(estudiante.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email ya existe");
        }
        validateCursos(estudiante.getCursos());
        return repository.save(estudiante);
    }

    public List<Estudiante> listAll() {
        return repository.findAll();
    }

    public Optional<Estudiante> findById(Long id) {
        return repository.findById(id);
    }

    public Estudiante update(Long id, Estudiante estudiante) {
        return repository.findById(id).map(existing -> {
            if (estudiante.getNombre() != null && !estudiante.getNombre().isEmpty()) {
                existing.setNombre(estudiante.getNombre());
            }
            if (estudiante.getEmail() != null && !estudiante.getEmail().isEmpty()) {
                if (!existing.getEmail().equals(estudiante.getEmail()) && repository.findByEmail(estudiante.getEmail()).isPresent()) {
                    throw new IllegalArgumentException("Email ya existe");
                }
                existing.setEmail(estudiante.getEmail());
            }
            if (estudiante.getCursos() != null) {
                validateCursos(estudiante.getCursos());
                existing.getCursos().clear();
                existing.getCursos().addAll(estudiante.getCursos());
            }
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Set<Curso> listCursosByEstudianteId(Long id) {
        return repository.findById(id)
                .map(Estudiante::getCursos)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    private void validateCursos(Set<Curso> cursos) {
        if (cursos != null) {
            for (Curso curso : cursos) {
                if (curso.getId() == null || !cursoRepository.existsById(curso.getId())) {
                    throw new IllegalArgumentException("Curso no existe: " + curso.getId());
                }
            }
        }
    }
}