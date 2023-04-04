package io.github.raphaelrighetti.memotecaapi.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.raphaelrighetti.memotecaapi.entities.usuario.dto.UsuarioDTO;
import io.github.raphaelrighetti.memotecaapi.entities.usuario.dto.UsuarioTransactionalDTO;
import io.github.raphaelrighetti.memotecaapi.services.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/registrar")
public class RegistrarUsuarioController {

	@Autowired
	private UsuarioService service;
	
	@PostMapping
	@Transactional
	public ResponseEntity<UsuarioDTO> registrar(@RequestBody @Valid UsuarioTransactionalDTO dados, UriComponentsBuilder uriBuilder) {
		UsuarioDTO dto = service.registrar(dados);
		
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(dto.id()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
}
