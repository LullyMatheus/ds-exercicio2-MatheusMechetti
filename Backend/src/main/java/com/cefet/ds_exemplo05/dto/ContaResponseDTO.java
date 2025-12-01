package com.cefet.ds_exemplo05.dto;

import com.cefet.ds_exemplo05.entities.Conta;

public class ContaResponseDTO {

    private Long id;    
    private String numero;
    private Double saldo;
    private Long idCliente;
    
    public ContaResponseDTO() {
	}  
    
    public ContaResponseDTO(Conta conta) {
        this.id = conta.getId();
        this.numero = conta.getNumero();
        this.saldo = conta.getSaldo();
        this.idCliente = conta.getCliente().getId();
    }

	public Long getId() {
		return id;
	}

	public String getNumero() {
		return numero;
	}

	public Double getSaldo() {
		return saldo;
	}

	public Long getIdCliente() {
		return idCliente;
	}

}
