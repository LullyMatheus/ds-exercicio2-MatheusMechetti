package com.cefet.ds_exemplo05.dto;

import com.cefet.ds_exemplo05.entities.Lancamento;
import com.cefet.ds_exemplo05.entities.TipoLancamento;

public class LancamentoResponseDTO {

    private Long id;    
    private Double valor;
    private TipoLancamento tipo;
    private Long idConta; 
    
    public LancamentoResponseDTO() {
	}  
    
    public LancamentoResponseDTO(Lancamento lancamento) {
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
