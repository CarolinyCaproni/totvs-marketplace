package com.totvs.totvs_marketplace_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.totvs.totvs_marketplace_backend.model.Produto;

public interface ProdutoService {

	Produto cadastrar(Produto produto);

    Optional<Produto> buscarPorId(Long id);

    Page<Produto> listarTodos(Pageable pageable);

    Page<Produto> buscarPorNome(String nome, Pageable pageable);

    Page<Produto> buscarPorCategoria(Long categoriaId, Pageable pageable);

    Page<Produto> buscarPorNomeECategoria(String nome, Long categoriaId, Pageable pageable);

    Produto editar(Long id, Produto produto);

    void excluir(Long id);

    long contarProdutosComEstoque();

    List<Object[]> obterQuantidadeTotalEstoquePorCategoria();

}
