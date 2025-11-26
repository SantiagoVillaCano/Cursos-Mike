package com.cursos.cursosApi.service;

import com.cursos.cursosApi.entity.Categoria;
import com.cursos.cursosApi.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    public Categoria create(Categoria categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().isEmpty()) {
            throw new IllegalArgumentException("Nombre requerido");
        }
        return repository.save(categoria);
    }

    public List<Categoria> listAll() {
        return repository.findAll();
    }

    public Optional<Categoria> findById(Long id) {
        return repository.findById(id);
    }

    public Categoria update(Long id, Categoria categoria) {
        return repository.findById(id).map(existing -> {
            if (categoria.getNombre() != null && !categoria.getNombre().isEmpty()) {
                existing.setNombre(categoria.getNombre());
            }
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}