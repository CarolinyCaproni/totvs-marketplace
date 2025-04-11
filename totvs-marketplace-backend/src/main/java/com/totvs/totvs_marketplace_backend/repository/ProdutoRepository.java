package com.totvs.totvs_marketplace_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.totvs.totvs_marketplace_backend.model.Categoria;
import com.totvs.totvs_marketplace_backend.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	Optional<Produto> findByNomeAndCategoria(String nome, Categoria categoria);

    Optional<Produto> findById(Long id);

    Page<Produto> findAll(Pageable pageable);

    Page<Produto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Produto> findByCategoria(Categoria categoria, Pageable pageable);

    Page<Produto> findByNomeContainingIgnoreCaseAndCategoria(String nome, Categoria categoria, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Produto p WHERE p.quantidadeEstoque > 0")
    long contarProdutosComEstoque();

    @Query("SELECT c.nome, SUM(p.quantidadeEstoque) FROM Produto p JOIN p.categoria c GROUP BY c.nome")
    List<Object[]> sumQuantidadeEstoquePorCategoria();


}
