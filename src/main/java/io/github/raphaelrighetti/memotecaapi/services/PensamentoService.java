package io.github.raphaelrighetti.memotecaapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.raphaelrighetti.memotecaapi.entities.pensamento.Pensamento;
import io.github.raphaelrighetti.memotecaapi.entities.pensamento.dto.PensamentoAtualizacaoDTO;
import io.github.raphaelrighetti.memotecaapi.entities.pensamento.dto.PensamentoCadastroDTO;
import io.github.raphaelrighetti.memotecaapi.entities.pensamento.dto.PensamentoDTO;
import io.github.raphaelrighetti.memotecaapi.repositories.PensamentoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PensamentoService {

	@Autowired
	private PensamentoRepository repository;
	
	public PensamentoDTO cadastrar(PensamentoCadastroDTO dados) {
		Pensamento pensamento = new Pensamento(null, dados.conteudo(), dados.autoria(), dados.modelo(), false);
		
		repository.save(pensamento);
		
		return new PensamentoDTO(pensamento);
	}
	
	public PensamentoDTO detalhar(Long id) {
		Pensamento pensamento = repository.getReferenceById(id);
		
		return new PensamentoDTO(pensamento);
	}
	
	public Page<PensamentoDTO> listar(Pageable pageable) {
		Page<PensamentoDTO> page = repository.findAll(pageable).map(PensamentoDTO::new);
		
		return page;
	}
	
	public Page<PensamentoDTO> listarPorFiltro(String filtro, Pageable pageable) {
		Page<PensamentoDTO> page = repository.pensamentosPorFiltro(filtro, pageable).map(PensamentoDTO::new);
		
		return page;
	}
	
	public Page<PensamentoDTO> listarFavoritos(Pageable pageable) {
		Page<PensamentoDTO> page = repository.findByFavoritoTrue(pageable).map(PensamentoDTO::new);
		
		return page;
	}
	
	public Page<PensamentoDTO> listarFavoritosPorFiltro(String filtro, Pageable pageable) {
		Page<PensamentoDTO> page = repository.pensamentosFavoritosPorFiltro(filtro, pageable).map(PensamentoDTO::new);
		
		return page;
	}
	
	public PensamentoDTO atualizar(Long id, PensamentoAtualizacaoDTO dados) {
		Pensamento pensamento = repository.getReferenceById(id);
		
		pensamento.atualizar(dados);
		
		return new PensamentoDTO(pensamento);
	}
	
	public void excluir(Long id) {
		if (!repository.existsById(id)) {
			throw new EntityNotFoundException();
		}
		
		repository.deleteById(id);
	}
}
