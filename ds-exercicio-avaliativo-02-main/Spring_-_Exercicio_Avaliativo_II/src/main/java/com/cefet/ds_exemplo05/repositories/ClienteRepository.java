package com.cefet.ds_exemplo05.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cefet.ds_exemplo05.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	boolean existsByCpf(String cpf);
	Optional<Cliente> findByCpfAndSenha(String cpf, String senha);
}
