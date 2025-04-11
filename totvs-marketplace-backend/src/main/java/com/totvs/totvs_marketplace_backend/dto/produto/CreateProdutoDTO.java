package com.totvs.totvs_marketplace_backend.dto.produto;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

public class CreateProdutoDTO {

    @NotNull
    @NotBlank
    private String nome;

    private String descricao;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal preco;

    @NotNull
    @Min(0)
    private Integer quantidadeEstoque;

    @NotNull
    private Long categoriaId;

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }
}
