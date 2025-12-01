package com.cefet.ds_exemplo05.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefet.ds_exemplo05.dto.ContaRequestDTO;
import com.cefet.ds_exemplo05.dto.ContaResponseDTO;
import com.cefet.ds_exemplo05.entities.Cliente;
import com.cefet.ds_exemplo05.entities.Conta;
import com.cefet.ds_exemplo05.repositories.ClienteRepository;
import com.cefet.ds_exemplo05.repositories.ContaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	// Buscar todos
	public List<ContaResponseDTO> findAll() {
		List<Conta> contas = contaRepository.findAll();
		return contas.stream().map(ContaResponseDTO::new).toList();
	}

	// Buscar por id
	public Optional<ContaResponseDTO> findById(Long id) {
		Conta conta = contaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Conta não localizada com ID: " + id));
		
		return Optional.of(new ContaResponseDTO(conta));
	}

	// Salvar ou atualizar
	public ContaResponseDTO save(ContaRequestDTO contaRequestDTO) {
		Cliente cliente = clienteRepository.findById(contaRequestDTO.getIdCliente())
				.orElseThrow(() -> new EntityNotFoundException("Cliente não localizado com ID: " + contaRequestDTO.getIdCliente()));
		
		Conta conta;
	    
	    if (contaRequestDTO.getId() != null) {
	        // Atualização: buscar conta existente
	        conta = contaRepository.findById(contaRequestDTO.getId())
	                .orElseThrow(() -> new EntityNotFoundException(
	                        "Conta não localizada com ID: " + contaRequestDTO.getId()));

	        //Verifica tentativa de alteração do número
	        if (!conta.getNumero().equals(contaRequestDTO.getNumero())) {
	            throw new IllegalArgumentException("O número da conta não pode ser alterado após o cadastro.");
	        }
	        
	        // Atualiza apenas os campos permitidos
	        conta.setCliente(cliente);	        
	 	        
	        // saldo não é alterado
	    } else {
	        // Inserção: criar nova conta com saldo zero
	        conta = new Conta();
	        conta.setNumero(contaRequestDTO.getNumero());
	        conta.setCliente(cliente);
	        conta.setSaldo(0.0); // saldo inicial
	    }		
		
		Conta contaSalva = contaRepository.save(conta);
        return new ContaResponseDTO(contaSalva);
	}

	// Excluir por id
	public void delete(Long id) {
        if (!contaRepository.existsById(id)) {
            throw new EntityNotFoundException("Conta não localizada com ID: " + id);
        }		
        contaRepository.deleteById(id);
	}
	
	//verificar por id
	public boolean existsById(Long id) {
	    return contaRepository.existsById(id);
	}
	
	// Buscar por ID do cliente
	public List<ContaResponseDTO> findByClienteId(Long idCliente) {
		List<Conta> contas = contaRepository.findByClienteId(idCliente);
		return contas.stream().map(ContaResponseDTO::new).toList();
	}
	
	//verificar por id
	public boolean existsByNumero(String numero) {
	    return contaRepository.existsByNumero(numero);
	}		
}
