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

import com.cefet.ds_exemplo05.dto.ContaRequestDTO;
import com.cefet.ds_exemplo05.dto.ContaResponseDTO;
import com.cefet.ds_exemplo05.services.ContaService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contas")
public class ContaController {
	
	@Autowired
	private ContaService contaService;

    @GetMapping
    public ResponseEntity<List<ContaResponseDTO>> findAll() {
        List<ContaResponseDTO> contasResponseDTO = contaService.findAll();
        return ResponseEntity.ok(contasResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ContaResponseDTO>> findById(@PathVariable Long id) {
        Optional<ContaResponseDTO> contaResponseDTO = contaService.findById(id);
        return ResponseEntity.ok(contaResponseDTO);
    }
    
    @PostMapping
    public ResponseEntity<ContaResponseDTO> create(@Valid @RequestBody ContaRequestDTO contaRequestDTO) {
    	ContaResponseDTO contaResponseDTO = contaService.save(contaRequestDTO);
        return ResponseEntity.status(201).body(contaResponseDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ContaRequestDTO contaRequestDTO) {
    	// Lança exceção se o ID estiver ausente
        if (contaRequestDTO.getId() == null) {
            throw new IllegalArgumentException("O campo 'id' é obrigatório para atualização.");
        }

        // Lança exceção se o ID do path for diferente do ID do DTO
        if (!id.equals(contaRequestDTO.getId())) {
            throw new IllegalArgumentException("O ID informado na URL é diferente do ID do corpo da requisição.");
        }
        
        // Lança exceção se o ID não existir
        if (!contaService.existsById(id)) {
            throw new EntityNotFoundException("Conta com ID " + id + " não localizado.");
        }  
        
        ContaResponseDTO contaResponseDTO = contaService.save(contaRequestDTO);
        return ResponseEntity.ok(contaResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {        
    	contaService.delete(id);
        return ResponseEntity.noContent().build();
    }
        
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<ContaResponseDTO>>  findByClienteId(@PathVariable Long idCliente) {       
        List<ContaResponseDTO> contasResponseDTO = contaService.findByClienteId(idCliente);
        return ResponseEntity.ok(contasResponseDTO);        
    }
    
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByNumero(@RequestParam String numero) {
        boolean exists = contaService.existsByNumero(numero);
        return ResponseEntity.ok(exists);
    }    
}
