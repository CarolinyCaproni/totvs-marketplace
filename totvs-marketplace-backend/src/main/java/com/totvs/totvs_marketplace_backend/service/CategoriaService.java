package com.totvs.totvs_marketplace_backend.service;

import java.util.List;
import java.util.Optional;

import com.totvs.totvs_marketplace_backend.model.Categoria;

public interface CategoriaService {
	
	Categoria cadastrar(Categoria categoria);

    Optional<Categoria> buscarPorId(Long id);

    List<Categoria> listarTodas();

    Categoria editar(Long id, Categoria categoria);

    void excluir(Long id);

}
