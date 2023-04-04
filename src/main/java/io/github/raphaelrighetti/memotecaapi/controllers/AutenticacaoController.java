package io.github.raphaelrighetti.memotecaapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.raphaelrighetti.memotecaapi.entities.usuario.dto.UsuarioTransactionalDTO;
import io.github.raphaelrighetti.memotecaapi.services.security.JWTService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private JWTService jwtService;
	
	@PostMapping
	public ResponseEntity<JWTDTO> autenticar(@RequestBody @Valid UsuarioTransactionalDTO dados) {
		UsernamePasswordAuthenticationToken authenticationToken 
				= new UsernamePasswordAuthenticationToken(dados.username(), dados.senha());
		
		Authentication authentication = manager.authenticate(authenticationToken);
		UserDetails usuario = (UserDetails) authentication.getPrincipal();
		
		JWTDTO dto = new JWTDTO(jwtService.gerarToken(usuario));
		
		return ResponseEntity.ok(dto);
	}
	
	private record JWTDTO(String token) {
		
	}
}
