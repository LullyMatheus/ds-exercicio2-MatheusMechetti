package com.cefet.ds_exemplo05.dto;

import com.cefet.ds_exemplo05.entities.Cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClienteRequestDTO {

    private Long id;    

    @NotBlank(message = "O campo 'nome' é obrigatório.")
    @Size(max = 200, message = "O nome não pode ter mais de 200 caracteres.")
    private String nome;

    @NotBlank(message = "O campo 'cpf' é obrigatório.")
    @Size(min = 11, max = 14, message = "O CPF deve ter entre 11 e 14 caracteres.")
    private String cpf; 

    @Size(min = 3, message = "A senha deve conter no mínimo 3 caracteres.")
    private String senha;     
    
    public ClienteRequestDTO() {
    }  

    public ClienteRequestDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.senha = cliente.getSenha();
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
    
    public String getSenha() {
        return senha;
    }      
}
