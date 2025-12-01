package com.cefet.ds_exemplo05.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cliente")
public class Cliente {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false, length = 200)
    private String nome;
    
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;  
    
	@Column(nullable = false, length = 200)
    private String senha;    
    
    public Cliente() {
	}    

    public Cliente(Long id, String nome, String cpf, String senha) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.cpf = senha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}	
}
