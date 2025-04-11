package com.totvs.totvs_marketplace_backend.controller;

import java.util.List;
import java.util.Optional;

import com.totvs.totvs_marketplace_backend.dto.produto.CreateProdutoDTO;
import com.totvs.totvs_marketplace_backend.dto.produto.UpdateProdutoDTO;
import com.totvs.totvs_marketplace_backend.model.Categoria;
import com.totvs.totvs_marketplace_backend.service.CategoriaService;
import com.totvs.totvs_marketplace_backend.service.exception.RegraNegocioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.totvs.totvs_marketplace_backend.model.Produto;
import com.totvs.totvs_marketplace_backend.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

	private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<Produto> cadastrar(@Valid @RequestBody CreateProdutoDTO produtoDto) {
        logger.info("Requisição para cadastrar produto: {}", produtoDto);

        Categoria categoria = categoriaService.buscarPorId(produtoDto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        Produto produto = new Produto();
        produto.setNome(produtoDto.getNome());
        produto.setDescricao(produtoDto.getDescricao());
        produto.setPreco(produtoDto.getPreco());
        produto.setQuantidadeEstoque(produtoDto.getQuantidadeEstoque());
        produto.setCategoria(categoria);

        Produto novoProduto = produtoService.cadastrar(produto);

        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        logger.info("Requisição para buscar produto com ID: {}", id);
        Optional<Produto> produto = produtoService.buscarPorId(id);
        return produto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Produto>> listarTodos(@PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        logger.info("Requisição para listar todos os produtos (paginado).");
        Page<Produto> produtos = produtoService.listarTodos(pageable);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/nome")
    public ResponseEntity<Page<Produto>> buscarPorNome(@RequestParam String nome, @PageableDefault(size = 10) Pageable pageable) {
        logger.info("Requisição para buscar produtos por nome: {}", nome);
        Page<Produto> produtos = produtoService.buscarPorNome(nome, pageable);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<Page<Produto>> buscarPorCategoria(
            @PathVariable Long categoriaId,
            @PageableDefault(size = 10) Pageable pageable) {
        logger.info("Requisição para buscar produtos por categoria ID: {} (paginado).", categoriaId);
        Page<Produto> produtos = produtoService.buscarPorCategoria(categoriaId, pageable);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/filtro")
    public ResponseEntity<Page<Produto>> buscarPorNomeECategoria(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long categoriaId,
            @PageableDefault(size = 10) Pageable pageable) {
        logger.info("Requisição para buscar produtos por nome '{}' e categoria ID '{}' (paginado).", nome, categoriaId);
        Page<Produto> produtos;

        if (nome != null && categoriaId != null) {
            produtos = produtoService.buscarPorNomeECategoria(nome, categoriaId, pageable);
        } else if (nome != null) {
            produtos = produtoService.buscarPorNome(nome, pageable);
        } else if (categoriaId != null) {
            produtos = produtoService.buscarPorCategoria(categoriaId, pageable);
        } else {
            produtos = produtoService.listarTodos(pageable);
        }
        return ResponseEntity.ok(produtos);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Produto> editar(@PathVariable Long id, @Valid @RequestBody UpdateProdutoDTO produtoDto) {
        logger.info("Requisição para editar produto com ID {}: {}", id, produtoDto);

        try {
            Produto produtoExistente = produtoService.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            Categoria categoria = categoriaService.buscarPorId(produtoDto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

            produtoExistente.setNome(produtoDto.getNome());
            produtoExistente.setDescricao(produtoDto.getDescricao());
            produtoExistente.setPreco(produtoDto.getPreco());
            produtoExistente.setQuantidadeEstoque(produtoDto.getQuantidadeEstoque());
            produtoExistente.setCategoria(categoria);

            Produto produtoAtualizado = produtoService.editar(id, produtoExistente);

            return ResponseEntity.ok(produtoAtualizado);

        } catch (RuntimeException e) {
            logger.error("Erro ao editar produto: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        logger.info("Requisição para excluir produto com ID: {}", id);
        try {
            produtoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/estoque/total")
    public ResponseEntity<Long> contarProdutosComEstoque() {
        logger.info("Requisição para obter a contagem total de produtos com estoque > 0.");
        long count = produtoService.contarProdutosComEstoque();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/estoque/categoria")
    public ResponseEntity<List<Object[]>> obterQuantidadeTotalEstoquePorCategoria() {
        logger.info("Requisição para obter a quantidade total de produtos em estoque por categoria.");
        List<Object[]> estoquePorCategoria = produtoService.obterQuantidadeTotalEstoquePorCategoria();
        return ResponseEntity.ok(estoquePorCategoria);
    }

}
