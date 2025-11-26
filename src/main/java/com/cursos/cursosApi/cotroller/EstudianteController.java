package com.cursos.cursosApi.cotroller;

import com.cursos.cursosApi.entity.Estudiante;
import com.cursos.cursosApi.entity.Curso;
import com.cursos.cursosApi.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    @Autowired
    private EstudianteService service;

    @PostMapping
    public Estudiante create(@RequestBody Estudiante estudiante) {
        return service.create(estudiante);
    }

    @GetMapping
    public List<Estudiante> list() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Estudiante update(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        return service.update(id, estudiante);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/cursos")
    public Set<Curso> listCursos(@PathVariable Long id) {
        return service.listCursosByEstudianteId(id);
    }
}