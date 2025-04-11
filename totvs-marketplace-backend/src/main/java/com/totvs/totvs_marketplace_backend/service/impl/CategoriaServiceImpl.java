package com.totvs.totvs_marketplace_backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.totvs.totvs_marketplace_backend.model.Categoria;
import com.totvs.totvs_marketplace_backend.repository.CategoriaRepository;
import com.totvs.totvs_marketplace_backend.service.CategoriaService;
import com.totvs.totvs_marketplace_backend.service.exception.RegraNegocioException;

import jakarta.transaction.Transactional;

@Service
public class CategoriaServiceImpl implements CategoriaService {
	
	@Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public Categoria cadastrar(Categoria categoria) {
        Optional<Categoria> categoriaExistente = categoriaRepository.findByNome(categoria.getNome());
        if (categoriaExistente.isPresent()) {
            throw new RegraNegocioException("Já existe uma categoria com este nome.");
        }
        return categoriaRepository.save(categoria);
    }

    @Override
    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    @Override
    @Transactional
    public Categoria editar(Long id, Categoria categoria) {
        Optional<Categoria> categoriaExistenteOptional = categoriaRepository.findById(id);
        if (categoriaExistenteOptional.isEmpty()) {
            throw new RegraNegocioException("Categoria não encontrada.");
        }
        Categoria categoriaExistente = categoriaExistenteOptional.get();

        Optional<Categoria> outraCategoriaComMesmoNome = categoriaRepository.findByNome(categoria.getNome());
        if (outraCategoriaComMesmoNome.isPresent() && !outraCategoriaComMesmoNome.get().getId().equals(id)) {
            throw new RegraNegocioException("Já existe outra categoria com este nome.");
        }

        categoriaExistente.setNome(categoria.getNome());
        return categoriaRepository.save(categoriaExistente);
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
        if (categoriaOptional.isEmpty()) {
            throw new RegraNegocioException("Categoria não encontrada.");
        }
        
        if (!categoriaOptional.get().getProdutos().isEmpty()) {
            throw new RegraNegocioException("Não é possível excluir uma categoria que possui produtos associados.");
        }
        categoriaRepository.deleteById(id);
    }

}
