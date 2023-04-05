package io.github.raphaelrighetti.memotecaapi.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.raphaelrighetti.memotecaapi.entities.pensamento.dto.PensamentoAtualizacaoDTO;
import io.github.raphaelrighetti.memotecaapi.entities.pensamento.dto.PensamentoCadastroDTO;
import io.github.raphaelrighetti.memotecaapi.entities.pensamento.dto.PensamentoDTO;
import io.github.raphaelrighetti.memotecaapi.services.PensamentoService;
import io.github.raphaelrighetti.memotecaapi.services.PensamentoUsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pensamentos")
public class PensamentoController {
	
	@Autowired
	private PensamentoService pensamentoService;
	
	@Autowired
	private PensamentoUsuarioService pensamentoUsuarioService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<PensamentoDTO> cadastrar(
			@RequestHeader(name = "Authorization") String header, @RequestBody @Valid PensamentoCadastroDTO dados, UriComponentsBuilder uriBuilder) {
		PensamentoDTO dto = pensamentoService.cadastrar(header, dados);
		
		URI uri = uriBuilder.path("/pensamentos/{id}").buildAndExpand(dto.id()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PensamentoDTO> detalhar(@RequestHeader(name = "Authorization") String header, @PathVariable Long id) {
		PensamentoDTO dto = pensamentoService.detalhar(header, id);
		
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping
	public ResponseEntity<Page<PensamentoDTO>> listar(@RequestParam(required = false) String filtro, Pageable pageable) {
		Page<PensamentoDTO> page;
		
		if (filtro != null) {
			page = pensamentoService.listarPorFiltro(filtro, pageable);
		} else {
			page = pensamentoService.listar(pageable);
		}
		
		return ResponseEntity.ok(page);
	}
	
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<Page<PensamentoDTO>> listarDoUsuario(
			@RequestHeader(name = "Authorization") String header, @PathVariable Long usuarioId, @RequestParam(required = false) String filtro, Pageable pageable) {
		Page<PensamentoDTO> page;
		
		if (filtro != null) {
			page = pensamentoUsuarioService.listarPorFiltro(header, usuarioId, filtro, pageable);
		} else {
			page = pensamentoUsuarioService.listar(header, usuarioId, pageable);
		}
		
		return ResponseEntity.ok(page);
	}
	
	@GetMapping("/usuario/{usuarioId}/favoritos")
	public ResponseEntity<Page<PensamentoDTO>> listarFavoritosDoUsuario(
			@RequestHeader(name = "Authorization") String header, @PathVariable Long usuarioId, @RequestParam(required = false) String filtro, Pageable pageable) {
		Page<PensamentoDTO> page;
		
		if (filtro != null) {
			page = pensamentoUsuarioService.listarFavoritosPorFiltro(header, usuarioId, filtro, pageable);
		} else {
			page = pensamentoUsuarioService.listarFavoritos(header, usuarioId, pageable);
		}
		
		return ResponseEntity.ok(page);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<PensamentoDTO> atualizar(@RequestHeader(name = "Authorization") String header, @PathVariable Long id, @RequestBody @Valid PensamentoAtualizacaoDTO dados) {
		PensamentoDTO dto = pensamentoService.atualizar(header, id, dados);
		
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> excluir(@RequestHeader(name = "Authorization") String header, @PathVariable Long id) {
		pensamentoService.excluir(header, id);
		
		return ResponseEntity.noContent().build();
	}
}
