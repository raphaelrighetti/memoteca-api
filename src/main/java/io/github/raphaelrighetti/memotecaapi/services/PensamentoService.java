package io.github.raphaelrighetti.memotecaapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.raphaelrighetti.memotecaapi.entities.pensamento.Pensamento;
import io.github.raphaelrighetti.memotecaapi.entities.pensamento.dto.PensamentoAtualizacaoDTO;
import io.github.raphaelrighetti.memotecaapi.entities.pensamento.dto.PensamentoCadastroDTO;
import io.github.raphaelrighetti.memotecaapi.entities.pensamento.dto.PensamentoDTO;
import io.github.raphaelrighetti.memotecaapi.entities.usuario.Usuario;
import io.github.raphaelrighetti.memotecaapi.repositories.PensamentoRepository;
import io.github.raphaelrighetti.memotecaapi.repositories.UsuarioRepository;

@Service
public class PensamentoService {

	@Autowired
	private PensamentoRepository pensamentoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public PensamentoDTO cadastrar(String header, PensamentoCadastroDTO dados) {
		usuarioService.compararUsuarios(header, dados.usuarioId());
		
		Usuario usuario = usuarioRepository.getReferenceById(dados.usuarioId());
		Pensamento pensamento = new Pensamento(null, dados.conteudo(), dados.autoria(), dados.modelo(), false, dados.privado(), usuario);
		
		pensamentoRepository.save(pensamento);
		
		return new PensamentoDTO(pensamento);
	}
	
	public PensamentoDTO detalhar(String header, Long id) {
		Pensamento pensamento = pensamentoRepository.getReferenceById(id);
		
		usuarioService.compararUsuarios(header, pensamento.getUsuario().getId());
		
		return new PensamentoDTO(pensamento);
	}
	
	public Page<PensamentoDTO> listar(Pageable pageable) {
		Page<PensamentoDTO> page = pensamentoRepository.pensamentos(pageable).map(PensamentoDTO::new);
		
		return page;
	}
	
	public Page<PensamentoDTO> listarPorFiltro(String filtro, Pageable pageable) {
		Page<PensamentoDTO> page = pensamentoRepository.pensamentosPorFiltro(filtro, pageable).map(PensamentoDTO::new);
		
		return page;
	}
	
	public PensamentoDTO atualizar(String header, Long id, PensamentoAtualizacaoDTO dados) {
		Pensamento pensamento = pensamentoRepository.getReferenceById(id);
		
		usuarioService.compararUsuarios(header, pensamento.getUsuario().getId());
		
		pensamento.atualizar(dados);
		
		return new PensamentoDTO(pensamento);
	}
	
	public void excluir(String header, Long id) {
		Pensamento pensamento = pensamentoRepository.getReferenceById(id);
		
		usuarioService.compararUsuarios(header, pensamento.getUsuario().getId());
		
		pensamentoRepository.delete(pensamento);
	}
}
