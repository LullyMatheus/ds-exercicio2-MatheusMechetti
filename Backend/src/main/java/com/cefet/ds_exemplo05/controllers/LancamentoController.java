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
import org.springframework.web.bind.annotation.RestController;

import com.cefet.ds_exemplo05.dto.LancamentoRequestDTO;
import com.cefet.ds_exemplo05.dto.LancamentoResponseDTO;
import com.cefet.ds_exemplo05.services.LancamentoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {
	
	@Autowired
	private LancamentoService lancamentoService;

    @GetMapping
    public ResponseEntity<List<LancamentoResponseDTO>> findAll() {
        List<LancamentoResponseDTO> lancamentosResponseDTO = lancamentoService.findAll();
        return ResponseEntity.ok(lancamentosResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<LancamentoResponseDTO>> findById(@PathVariable Long id) {
        Optional<LancamentoResponseDTO> lancamentoResponseDTO = lancamentoService.findById(id);
        return ResponseEntity.ok(lancamentoResponseDTO);
    }
    
    @PostMapping
    public ResponseEntity<LancamentoResponseDTO> create(@Valid @RequestBody LancamentoRequestDTO lancamentoRequestDTO) {
    	LancamentoResponseDTO lancamentoResponseDTO = lancamentoService.save(lancamentoRequestDTO);
        return ResponseEntity.status(201).body(lancamentoResponseDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<LancamentoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody LancamentoRequestDTO lancamentoRequestDTO) {
    	// Lança exceção se o ID estiver ausente
        if (lancamentoRequestDTO.getId() == null) {
            throw new IllegalArgumentException("O campo 'id' é obrigatório para atualização.");
        }

        // Lança exceção se o ID do path for diferente do ID do DTO
        if (!id.equals(lancamentoRequestDTO.getId())) {
            throw new IllegalArgumentException("O ID informado na URL é diferente do ID do corpo da requisição.");
        }   
        
        // Lança exceção se o ID não existir
        if (!lancamentoService.existsById(id)) {
            throw new EntityNotFoundException("Lançamento com ID " + id + " não localizado.");
        }        

        LancamentoResponseDTO lancamentoResponseDTO = lancamentoService.save(lancamentoRequestDTO);
        return ResponseEntity.ok(lancamentoResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
    	lancamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/conta/{idConta}")
    public ResponseEntity<List<LancamentoResponseDTO>>  findByContaId(@PathVariable Long idConta) {       
        List<LancamentoResponseDTO> lancamentosResponseDTO = lancamentoService.findByContaId(idConta);
        return ResponseEntity.ok(lancamentosResponseDTO);       
    }    
}
