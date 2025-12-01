package com.cefet.ds_exemplo05.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cefet.ds_exemplo05.entities.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{
	List<Lancamento> findByContaId(Long idConta);	
}
