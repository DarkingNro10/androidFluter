package com.example.categoria.controller;

import com.example.categoria.entity.Categoria;
import com.example.categoria.service.CategoriaService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping()
    public ResponseEntity<List<Categoria>> list() {
        return ResponseEntity.ok().body(categoriaService.listar());
    }

    @PostMapping()
    public ResponseEntity<Categoria> save(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.guardar(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable Integer id, @RequestBody Categoria categoria) {
        return categoriaService.actualizar(id, categoria)
                .map(updatedCategoria -> ResponseEntity.ok().body(updatedCategoria))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @CircuitBreaker(name = "listByIdCB", fallbackMethod = "fallBacklistById")
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> listById(@PathVariable(required = true) Integer id) {
        Optional<Categoria> categoria = categoriaService.listarPorId(id);
        return categoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @CircuitBreaker(name = "deleteByIdCB", fallbackMethod = "fallBackDeleteById")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(required = true) Integer id) {
        try {
            categoriaService.eliminarPorId(id);
            return ResponseEntity.ok("Eliminacion Correcta");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el registro.");
        }
    }

    // Resiliencia
    private ResponseEntity<Categoria> fallBacklistById(@PathVariable(required = true) Integer id, RuntimeException e) {
        Categoria categoria = new Categoria();
        categoria.setId(90000);
        categoria.setTitulo("Recurso no disponible del Titulo de la categoria");
        categoria.setDescripccion("Recurso no disponible de la Descripccion de la categoria");

        return ResponseEntity.ok().body(categoria);
    }

    private ResponseEntity<String> fallBackDeleteById(@PathVariable(required = true) Integer id, RuntimeException e) {
        // Aquí puedes devolver una respuesta alternativa en caso de error, por ejemplo:
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el registro.");
    }
}
