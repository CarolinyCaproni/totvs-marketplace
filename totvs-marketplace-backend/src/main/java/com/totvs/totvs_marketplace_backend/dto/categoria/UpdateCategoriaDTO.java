package com.totvs.totvs_marketplace_backend.dto.categoria;

import jakarta.validation.constraints.NotBlank;

public class UpdateCategoriaDTO {

    private String nome;

    public UpdateCategoriaDTO() {}

    public UpdateCategoriaDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
