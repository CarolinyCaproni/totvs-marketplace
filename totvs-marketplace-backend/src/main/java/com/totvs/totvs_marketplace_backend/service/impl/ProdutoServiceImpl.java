package com.totvs.totvs_marketplace_backend.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.totvs.totvs_marketplace_backend.model.Categoria;
import com.totvs.totvs_marketplace_backend.model.Produto;
import com.totvs.totvs_marketplace_backend.repository.CategoriaRepository;
import com.totvs.totvs_marketplace_backend.repository.ProdutoRepository;
import com.totvs.totvs_marketplace_backend.service.ProdutoService;
import com.totvs.totvs_marketplace_backend.service.exception.RegraNegocioException;

import jakarta.transaction.Transactional;

@Service
public class ProdutoServiceImpl implements ProdutoService {
	
	@Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public Produto cadastrar(Produto produto) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(produto.getCategoria().getId());
        if (categoriaOptional.isEmpty()) {
            throw new RegraNegocioException("Categoria não encontrada.");
        }
        produto.setCategoria(categoriaOptional.get());

        Optional<Produto> produtoExistente = produtoRepository.findByNomeAndCategoria(produto.getNome(), produto.getCategoria());
        if (produtoExistente.isPresent()) {
            throw new RegraNegocioException("Já existe um produto com este nome nesta categoria.");
        }

        if (produto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("O preço do produto deve ser maior que zero.");
        }

        if (produto.getQuantidadeEstoque() < 0) {
            throw new RegraNegocioException("A quantidade em estoque não pode ser negativa.");
        }

        return produtoRepository.save(produto);
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    @Override
    public Page<Produto> listarTodos(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    @Override
    public Page<Produto> buscarPorNome(String nome, Pageable pageable) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    @Override
    public Page<Produto> buscarPorCategoria(Long categoriaId, Pageable pageable) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(categoriaId);
        if (categoriaOptional.isEmpty()) {
            throw new RegraNegocioException("Categoria não encontrada.");
        }
        return produtoRepository.findByCategoria(categoriaOptional.get(), pageable);
    }

    @Override
    public Page<Produto> buscarPorNomeECategoria(String nome, Long categoriaId, Pageable pageable) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(categoriaId);
        if (categoriaOptional.isEmpty()) {
            throw new RegraNegocioException("Categoria não encontrada.");
        }
        return produtoRepository.findByNomeContainingIgnoreCaseAndCategoria(nome, categoriaOptional.get(), pageable);
    }

    @Override
    @Transactional
    public Produto editar(Long id, Produto produto) {
        Optional<Produto> produtoExistenteOptional = produtoRepository.findById(id);
        if (produtoExistenteOptional.isEmpty()) {
            throw new RegraNegocioException("Produto não encontrado.");
        }
        Produto produtoExistente = produtoExistenteOptional.get();

        Optional<Categoria> categoriaOptional = categoriaRepository.findById(produto.getCategoria().getId());
        if (categoriaOptional.isEmpty()) {
            throw new RegraNegocioException("Categoria não encontrada.");
        }

        Optional<Produto> outroProdutoComMesmoNomeNaCategoria = produtoRepository.findByNomeAndCategoria(produto.getNome(), categoriaOptional.get());
        if (outroProdutoComMesmoNomeNaCategoria.isPresent() && !outroProdutoComMesmoNomeNaCategoria.get().getId().equals(id)) {
            throw new RegraNegocioException("Já existe outro produto com este nome nesta categoria.");
        }

        produtoExistente.setNome(produto.getNome());
        produtoExistente.setDescricao(produto.getDescricao());
        produtoExistente.setPreco(produto.getPreco());
        produtoExistente.setQuantidadeEstoque(produto.getQuantidadeEstoque());
        produtoExistente.setCategoria(categoriaOptional.get());

        if (produtoExistente.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("O preço do produto deve ser maior que zero.");
        }

        if (produtoExistente.getQuantidadeEstoque() < 0) {
            throw new RegraNegocioException("A quantidade em estoque não pode ser negativa.");
        }

        return produtoRepository.save(produtoExistente);
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isEmpty()) {
            throw new RegraNegocioException("Produto não encontrado.");
        }
        if (produtoOptional.get().getQuantidadeEstoque() > 0) {
            throw new RegraNegocioException("Não é possível excluir um produto com quantidade em estoque maior que zero.");
        }
        produtoRepository.deleteById(id);
    }

    @Override
    public long contarProdutosComEstoque() {
        return produtoRepository.contarProdutosComEstoque();
    }

    @Override
    public List<Object[]> obterQuantidadeTotalEstoquePorCategoria() {
        return produtoRepository.sumQuantidadeEstoquePorCategoria();
    }

}
