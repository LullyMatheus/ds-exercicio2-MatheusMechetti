package com.cefet.ds_exemplo05.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefet.ds_exemplo05.dto.ClienteRequestDTO;
import com.cefet.ds_exemplo05.dto.ClienteResponseDTO;
import com.cefet.ds_exemplo05.entities.Cliente;
import com.cefet.ds_exemplo05.repositories.ClienteRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	// Buscar todos
	public List<ClienteResponseDTO> findAll() {
		List<Cliente> clientes = clienteRepository.findAll();
		return clientes.stream().map(ClienteResponseDTO::new).toList();
	}

	// Buscar por id
	public Optional<ClienteResponseDTO> findById(Long id) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Cliente não localizado com ID: " + id));
		
		return Optional.of(new ClienteResponseDTO(cliente));
	}

	// Salvar ou atualizar
	public ClienteResponseDTO save(ClienteRequestDTO clienteRequestDTO) {
		Cliente cliente = null;
		
		if (clienteRequestDTO.getId() == null) {
			cliente = new Cliente();
    		cliente.setSenha(clienteRequestDTO.getSenha());
    	}else {
    		cliente = clienteRepository.findById(clienteRequestDTO.getId())
    				.orElseThrow(() -> new EntityNotFoundException("Cliente não localizado com ID: " + clienteRequestDTO.getId()));
    	}
		
    	cliente.setNome(clienteRequestDTO.getNome());
    	cliente.setCpf(clienteRequestDTO.getCpf());
    	
    	Cliente clienteSalvo = clienteRepository.save(cliente);
        return new ClienteResponseDTO(clienteSalvo);
	}

	// Excluir por id
	public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente não localizado com ID: " + id);
        }		
		clienteRepository.deleteById(id);
	}
	
	//verificar id
	public boolean existsById(Long id) {
	    return clienteRepository.existsById(id);
	}	
	
	//verificar CPF
	public boolean existsByCpf(String cpf) {
	    return clienteRepository.existsByCpf(cpf);
	}
	
	// Autenticar 
	public Optional<ClienteResponseDTO> findByCpfAndSenha(String cpf, String senha) {
	    return clienteRepository.findByCpfAndSenha(cpf, senha)
	        .map(ClienteResponseDTO::new)
	        .or(() -> {
	            throw new EntityNotFoundException("CPF ou senha inválidos.");
	        });
	}
}


