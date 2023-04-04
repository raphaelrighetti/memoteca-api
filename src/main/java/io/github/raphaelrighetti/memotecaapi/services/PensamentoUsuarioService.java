package io.github.raphaelrighetti.memotecaapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.raphaelrighetti.memotecaapi.entities.pensamento.dto.PensamentoDTO;
import io.github.raphaelrighetti.memotecaapi.repositories.PensamentoRepository;

@Service
public class PensamentoUsuarioService {
	@Autowired
	private PensamentoRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public Page<PensamentoDTO> listar(String header, Long usuarioId, Pageable pageable) {
		usuarioService.compararUsuarios(header, usuarioId);
		
		Page<PensamentoDTO> page = repository.pensamentosDoUsuario(usuarioId, pageable).map(PensamentoDTO::new);
		
		return page;
	}
	
	public Page<PensamentoDTO> listarPorFiltro(String header, Long usuarioId, String filtro, Pageable pageable) {
		usuarioService.compararUsuarios(header, usuarioId);
		
		Page<PensamentoDTO> page = repository.pensamentosDoUsuarioPorFiltro(usuarioId, filtro, pageable).map(PensamentoDTO::new);
		
		return page;
	}
	
	public Page<PensamentoDTO> listarFavoritos(String header, Long usuarioId, Pageable pageable) {
		usuarioService.compararUsuarios(header, usuarioId);
		
		Page<PensamentoDTO> page = repository.pensamentosFavoritosDoUsuario(usuarioId, pageable).map(PensamentoDTO::new);
		
		return page;
	}
	
	public Page<PensamentoDTO> listarFavoritosPorFiltro(String header, Long usuarioId, String filtro, Pageable pageable) {
		usuarioService.compararUsuarios(header, usuarioId);
		
		Page<PensamentoDTO> page = repository.pensamentosFavoritosDoUsuarioPorFiltro(usuarioId, filtro, pageable).map(PensamentoDTO::new);
		
		return page;
	}
}
