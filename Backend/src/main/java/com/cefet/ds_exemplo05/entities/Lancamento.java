package com.cefet.ds_exemplo05.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_lancamento")
public class Lancamento {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double valor;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoLancamento tipo;    
    
    @ManyToOne 
    @JoinColumn(name = "id_conta", nullable = false)
    private Conta conta;
    
	public Lancamento() {
	}

	public Lancamento(Long id, Double valor, TipoLancamento tipo, Conta conta) {
		this.id = id;
		this.valor = valor;
		this.tipo = tipo;
		this.conta = conta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoLancamento tipo) {
		this.tipo = tipo;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}    
}
