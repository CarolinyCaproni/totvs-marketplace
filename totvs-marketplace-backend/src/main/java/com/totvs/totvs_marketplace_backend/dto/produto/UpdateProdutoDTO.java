package com.totvs.totvs_marketplace_backend.dto.produto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class UpdateProdutoDTO {
    private String nome;

    private String descricao;

    @DecimalMin("0.00")
    private BigDecimal preco;

    @Min(0)
    private Integer quantidadeEstoque;

    @NotNull
    private Long categoriaId;

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
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

    public Long getCategoriaId() {
        return categoriaId;
    }
}
