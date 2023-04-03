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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.raphaelrighetti.memotecaapi.entities.pensamento.dto.PensamentoAtualizacaoDTO;
import io.github.raphaelrighetti.memotecaapi.entities.pensamento.dto.PensamentoCadastroDTO;
import io.github.raphaelrighetti.memotecaapi.entities.pensamento.dto.PensamentoDTO;
import io.github.raphaelrighetti.memotecaapi.services.PensamentoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pensamentos")
public class PensamentoController {
	
	@Autowired
	private PensamentoService service;
	
	@PostMapping
	@Transactional
	public ResponseEntity<PensamentoDTO> cadastrar(@RequestBody @Valid PensamentoCadastroDTO dados, UriComponentsBuilder uriBuilder) {
		PensamentoDTO dto = service.cadastrar(dados);
		
		URI uri = uriBuilder.path("/pensamentos/{id}").buildAndExpand(dto.id()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PensamentoDTO> detalhar(@PathVariable Long id) {
		PensamentoDTO dto = service.detalhar(id);
		
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping
	public ResponseEntity<Page<PensamentoDTO>> listar(@RequestParam(required = false) String filtro, Pageable pageable) {
		Page<PensamentoDTO> page;
		
		if (filtro != null) {
			page = service.listarPorFiltro(filtro, pageable);
		} else {
			page = service.listar(pageable);
		}
		
		return ResponseEntity.ok(page);
	}
	
	@GetMapping("/favoritos")
	public ResponseEntity<Page<PensamentoDTO>> listarFavoritos(@RequestParam(required = false) String filtro, Pageable pageable) {
		Page<PensamentoDTO> page;
		
		if (filtro != null) {
			page = service.listarFavoritosPorFiltro(filtro, pageable);
		} else {
			page = service.listarFavoritos(pageable);
		}
		
		return ResponseEntity.ok(page);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<PensamentoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid PensamentoAtualizacaoDTO dados) {
		PensamentoDTO dto = service.atualizar(id, dados);
		
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		service.excluir(id);
		
		return ResponseEntity.noContent().build();
	}
}
