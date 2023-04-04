package io.github.raphaelrighetti.memotecaapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.raphaelrighetti.memotecaapi.entities.usuario.dto.UsuarioDTO;
import io.github.raphaelrighetti.memotecaapi.entities.usuario.dto.UsuarioTransactionalDTO;
import io.github.raphaelrighetti.memotecaapi.services.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> detalhar(@PathVariable Long id, @RequestHeader(name = "Authorization") String header) {
		header = header.replace("Bearer ", "");
		
		UsuarioDTO dto = service.detalhar(id, header);
		
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping
	public ResponseEntity<Page<UsuarioDTO>> listar(Pageable pageable) {
		Page<UsuarioDTO> page = service.listar(pageable);
		
		return ResponseEntity.ok(page);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioTransactionalDTO dados) {
		UsuarioDTO dto = service.atualizar(id, dados);
		
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		service.excluir(id);
		
		return ResponseEntity.noContent().build();
	}
}
