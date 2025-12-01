package com.cefet.ds_exemplo05.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cefet.ds_exemplo05.entities.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{
	boolean existsByNumero(String numero);
	List<Conta> findByClienteId(Long idCliente);	
}
