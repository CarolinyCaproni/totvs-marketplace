package com.totvs.totvs_marketplace_backend.controller;

import java.util.List;
import java.util.Optional;

import com.totvs.totvs_marketplace_backend.dto.categoria.UpdateCategoriaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.totvs.totvs_marketplace_backend.model.Categoria;
import com.totvs.totvs_marketplace_backend.service.CategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
	
	private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<Categoria> cadastrar(@Valid @RequestBody Categoria categoria) {
        logger.info("Requisição para cadastrar categoria: {}", categoria);
        Categoria novaCategoria = categoriaService.cadastrar(categoria);
        return new ResponseEntity<>(novaCategoria, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
        logger.info("Requisição para buscar categoria com ID: {}", id);
        Optional<Categoria> categoria = categoriaService.buscarPorId(id);
        return categoria.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodas() {
        logger.info("Requisição para listar todas as categorias.");
        List<Categoria> categorias = categoriaService.listarTodas();
        return ResponseEntity.ok(categorias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> editar(@PathVariable Long id, @Valid @RequestBody UpdateCategoriaDTO categoriaDTO) {
        logger.info("Requisição para editar categoria com ID {}: {}", id, categoriaDTO);
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDTO.getNome());
        try {
            Categoria categoriaAtualizada = categoriaService.editar(id, categoria);
            return ResponseEntity.ok(categoriaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        logger.info("Requisição para excluir categoria com ID: {}", id);
        try {
            categoriaService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Ou outro status adequado
        }
    }

}
