package com.cefet.ds_exemplo05.dto;

import com.cefet.ds_exemplo05.entities.Conta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ContaRequestDTO {

    private Long id;    

    @NotBlank(message = "O campo 'numero' é obrigatório.")
    @Size(max = 20, message = "O número da conta não pode ter mais de 20 caracteres.")
    private String numero;

    @NotNull(message = "O campo 'idCliente' é obrigatório.")
    private Long idCliente;

    public ContaRequestDTO() {
    }  

    public ContaRequestDTO(Conta conta) {
        this.id = conta.getId();
        this.numero = conta.getNumero();
        this.idCliente = conta.getCliente().getId();
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public Long getIdCliente() {
        return idCliente;
    }
}
