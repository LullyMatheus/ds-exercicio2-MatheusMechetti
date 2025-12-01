package com.cefet.ds_exemplo05.dto;

import com.cefet.ds_exemplo05.entities.Cliente;

public class ClienteResponseDTO {

    private Long id;    
    private String nome;
    private String cpf;
    
    public ClienteResponseDTO() {
	}  
    
    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
    }

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}   
	
	public String getCpf() {
		return cpf;
	} 	
	
}
