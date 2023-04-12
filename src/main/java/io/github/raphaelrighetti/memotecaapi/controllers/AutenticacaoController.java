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

import io.github.raphaelrighetti.memotecaapi.entities.token.dto.JWTDTO;
import io.github.raphaelrighetti.memotecaapi.entities.token.dto.RefreshTokenRequestDTO;
import io.github.raphaelrighetti.memotecaapi.entities.token.refresh.RefreshToken;
import io.github.raphaelrighetti.memotecaapi.entities.usuario.Usuario;
import io.github.raphaelrighetti.memotecaapi.entities.usuario.dto.UsuarioTransactionalDTO;
import io.github.raphaelrighetti.memotecaapi.services.RefreshTokenService;
import io.github.raphaelrighetti.memotecaapi.services.security.JWTService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Autowired
	private JWTService jwtService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<JWTDTO> autenticar(@RequestBody @Valid UsuarioTransactionalDTO dados) {
		UsernamePasswordAuthenticationToken authenticationToken 
				= new UsernamePasswordAuthenticationToken(dados.username(), dados.senha());
		
		Authentication authentication = manager.authenticate(authenticationToken);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		String token = jwtService.gerarToken(userDetails);
		Usuario usuario = (Usuario) userDetails;
		RefreshToken refreshToken = refreshTokenService.cadastrar(token, usuario);
		
		JWTDTO dto = new JWTDTO(usuario.getId(), usuario.getUsername(), token, refreshToken.getUuid());
		
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping("/refresh")
	@Transactional
	public ResponseEntity<JWTDTO> refreshToken(@RequestBody @Valid RefreshTokenRequestDTO dados) {
		JWTDTO dto = refreshTokenService.refreshToken(dados);
		
		return ResponseEntity.ok(dto);
	}
}
