package com.cefet.ds_exemplo05.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefet.ds_exemplo05.dto.LancamentoRequestDTO;
import com.cefet.ds_exemplo05.dto.LancamentoResponseDTO;
import com.cefet.ds_exemplo05.entities.Conta;
import com.cefet.ds_exemplo05.entities.Lancamento;
import com.cefet.ds_exemplo05.entities.TipoLancamento;
import com.cefet.ds_exemplo05.repositories.ContaRepository;
import com.cefet.ds_exemplo05.repositories.LancamentoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private ContaRepository contaRepository;

	// Buscar todos
	public List<LancamentoResponseDTO> findAll() {
		List<Lancamento> lancamentos = lancamentoRepository.findAll();
		return lancamentos.stream().map(LancamentoResponseDTO::new).toList();
	}

	// Buscar por id
	public Optional<LancamentoResponseDTO> findById(Long id) {
		Lancamento lancamento = lancamentoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Lançamento não localizado com ID: " + id));

		return Optional.of(new LancamentoResponseDTO(lancamento));
	}

	// Salvar ou atualizar
	public LancamentoResponseDTO save(LancamentoRequestDTO lancamentoRequestDTO) {
		Conta conta = contaRepository.findById(lancamentoRequestDTO.getIdConta()).orElseThrow(
				() -> new EntityNotFoundException("Conta não localizada com ID: " + lancamentoRequestDTO.getIdConta()));

		Lancamento lancamento;

		// Atualização
		if (lancamentoRequestDTO.getId() != null) {
			lancamento = lancamentoRepository.findById(lancamentoRequestDTO.getId())
					.orElseThrow(() -> new EntityNotFoundException(
							"Lançamento não localizado com ID: " + lancamentoRequestDTO.getId()));

			// ❗ Verifica tentativa de alteração do ID da conta
			if (!lancamento.getConta().getId().equals(lancamentoRequestDTO.getIdConta())) {
				throw new IllegalArgumentException("A conta do lançamento não pode ser alterada após o registro.");
			}
			
			// ❗ Verifica tentativa de alteração do valor do lançamento
			if (!lancamento.getValor().equals(lancamentoRequestDTO.getValor())) {
				throw new IllegalArgumentException("O valor do lançamento não pode ser alterada após o registro.");
			}
			
			// ❗ Verifica tentativa de alteração do tipo do lançamento
			if (!lancamento.getTipo().equals(lancamentoRequestDTO.getTipo())) {
				throw new IllegalArgumentException("o tipo do lançamento não pode ser alterada após o registro.");
			}		


		} else {
			// Inserção
			lancamento = new Lancamento();
			lancamento.setValor(lancamentoRequestDTO.getValor());
			lancamento.setTipo(lancamentoRequestDTO.getTipo());
			lancamento.setConta(conta);
		}

		// RN1: Atualizar saldo da conta conforme tipo do lançamento
		if (lancamento.getTipo().equals(TipoLancamento.DEPOSITO)) {

			conta.setSaldo(conta.getSaldo() + lancamento.getValor());

		} else if (lancamento.getTipo().equals(TipoLancamento.SAQUE)) {

			conta.setSaldo(conta.getSaldo() - lancamento.getValor());

		}

		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);

		// Atualiza saldo da conta no banco
		contaRepository.save(conta);

		return new LancamentoResponseDTO(lancamentoSalvo);
	}

	// Excluir por id
	public void delete(Long id) {
		Lancamento lancamento = lancamentoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Lançamento não localizado com ID: " + id));

		Conta conta = lancamento.getConta();

		// RN3: Criar lançamento para atualizar saldo antes de excluir
		if (lancamento.getTipo().equals(TipoLancamento.SAQUE)) {
			conta.setSaldo(conta.getSaldo() + lancamento.getValor());
		} else if (lancamento.getTipo().equals(TipoLancamento.DEPOSITO)) {
			conta.setSaldo(conta.getSaldo() - lancamento.getValor());
		}

		// Atualiza saldo da conta no banco
		contaRepository.save(conta);

		// Exclui o lançamento
		lancamentoRepository.deleteById(id);
	}

	// Buscar por ID da conta
	public List<LancamentoResponseDTO> findByContaId(Long idconta) {	
		List<Lancamento> lancamentos = lancamentoRepository.findByContaId(idconta);
		return lancamentos.stream().map(LancamentoResponseDTO::new).toList();
	}
	
	// verificar id
	public boolean existsById(Long id) {
		return lancamentoRepository.existsById(id);
	}

}
