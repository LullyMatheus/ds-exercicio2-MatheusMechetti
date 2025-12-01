package com.cefet.ds_exemplo05.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cefet.ds_exemplo05.dto.ClienteRequestDTO;
import com.cefet.ds_exemplo05.dto.ClienteResponseDTO;
import com.cefet.ds_exemplo05.services.ClienteService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> findAll() {
        List<ClienteResponseDTO> clientesResponseDTO = clienteService.findAll();
        return ResponseEntity.ok(clientesResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ClienteResponseDTO>> findById(@PathVariable Long id) {
        Optional<ClienteResponseDTO> clienteResponseDTO = clienteService.findById(id);
        return ResponseEntity.ok(clienteResponseDTO);
    }
    
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> create(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
    	ClienteResponseDTO clienteResponseDTO = clienteService.save(clienteRequestDTO);
        return ResponseEntity.status(201).body(clienteResponseDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {   	
    	// Lança exceção se o ID estiver ausente
        if (clienteRequestDTO.getId() == null) {
            throw new IllegalArgumentException("O campo 'id' é obrigatório para atualização.");
        }

        // Lança exceção se o ID do path for diferente do ID do DTO
        if (!id.equals(clienteRequestDTO.getId())) {
            throw new IllegalArgumentException("O ID informado na URL é diferente do ID do corpo da requisição.");
        }
        
        // Lança exceção se o ID não existir
        if (!clienteService.existsById(id)) {
            throw new EntityNotFoundException("Cliente com ID " + id + " não localizado.");
        }           
       
        ClienteResponseDTO clienteResponseDTO = clienteService.save(clienteRequestDTO);
        return ResponseEntity.ok(clienteResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
    	clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByCpf(@RequestParam String cpf) {
        boolean exists = clienteService.existsByCpf(cpf);
        return ResponseEntity.ok(exists);
    }    
    
    @PostMapping("/login")
    public ResponseEntity<ClienteResponseDTO> autenticar(@RequestBody ClienteRequestDTO loginDTO) {

        // valida se CPF e senha foram enviados
        if (loginDTO.getCpf() == null || loginDTO.getSenha() == null) {
            throw new IllegalArgumentException("CPF e senha são obrigatórios para autenticação.");
        }

        ClienteResponseDTO clienteResponseDTO = clienteService
                .findByCpfAndSenha(loginDTO.getCpf(), loginDTO.getSenha())
                .orElseThrow(() -> new EntityNotFoundException("CPF ou senha inválidos."));

        return ResponseEntity.ok(clienteResponseDTO);
    }    
}
