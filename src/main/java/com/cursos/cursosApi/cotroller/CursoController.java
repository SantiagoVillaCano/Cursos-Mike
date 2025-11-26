package com.cursos.cursosApi.cotroller;

import com.cursos.cursosApi.entity.Curso;
import com.cursos.cursosApi.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private CursoService service;

    @PostMapping
    public Curso create(@RequestBody Curso curso) {
        return service.create(curso);
    }

    @GetMapping
    public List<Curso> list() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Curso update(@PathVariable Long id, @RequestBody Curso curso) {
        return service.update(id, curso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}