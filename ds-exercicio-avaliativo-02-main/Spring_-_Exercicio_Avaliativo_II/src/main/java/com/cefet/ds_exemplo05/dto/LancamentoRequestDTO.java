package com.cefet.ds_exemplo05.dto;

import com.cefet.ds_exemplo05.entities.Lancamento;
import com.cefet.ds_exemplo05.entities.TipoLancamento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class LancamentoRequestDTO {

    private Long id;  

    @NotNull(message = "O campo 'valor' é obrigatório.")
    @PositiveOrZero(message = "O campo 'valor' não pode ser negativo.")
    private Double valor;

    @NotNull(message = "O campo 'tipo' é obrigatório.")
    private TipoLancamento tipo;

    @NotNull(message = "O campo 'idConta' é obrigatório.")
    private Long idConta;

    public LancamentoRequestDTO() {
    }  

    public LancamentoRequestDTO(Lancamento lancamento) {
        this.id = lancamento.getId();
        this.valor = lancamento.getValor();
        this.tipo = lancamento.getTipo();
        this.idConta = lancamento.getConta().getId();
    }

    public Long getId() {
        return id;
    }

    public Double getValor() {
        return valor;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public Long getIdConta() {
        return idConta;
    }
}
