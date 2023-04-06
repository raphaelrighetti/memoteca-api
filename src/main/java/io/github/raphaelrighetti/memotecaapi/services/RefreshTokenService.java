package io.github.raphaelrighetti.memotecaapi.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.raphaelrighetti.memotecaapi.entities.token.dto.JWTDTO;
import io.github.raphaelrighetti.memotecaapi.entities.token.dto.RefreshTokenRequestDTO;
import io.github.raphaelrighetti.memotecaapi.entities.token.refresh.RefreshToken;
import io.github.raphaelrighetti.memotecaapi.entities.usuario.Usuario;
import io.github.raphaelrighetti.memotecaapi.repositories.RefreshTokenRepository;
import io.github.raphaelrighetti.memotecaapi.services.security.JWTService;

@Service
public class RefreshTokenService {

	@Autowired
	private RefreshTokenRepository repository;
	
	@Autowired
	private JWTService jwtService;
	
	public RefreshToken cadastrar(String token, Usuario usuario) {
		RefreshToken refreshToken;
		
		if (repository.findByUsuario(usuario) != null) {
			refreshToken = repository.findByUsuario(usuario);
			
			refreshToken.setUuid(UUID.randomUUID().toString());
			refreshToken.setToken(token);
			refreshToken.setExpiracao(expiracao());
		} else {
			refreshToken = new RefreshToken();
			
			refreshToken.setToken(token);
			refreshToken.setUuid(UUID.randomUUID().toString());
			refreshToken.setUsuario(usuario);
			refreshToken.setExpiracao(expiracao());
			
			repository.save(refreshToken);
		}
		
		return refreshToken;
	}
	
	public JWTDTO refreshToken(RefreshTokenRequestDTO dados) {
		RefreshToken refreshToken = repository.findByUuid(dados.refreshToken());
		
		if (refreshToken == null) {
			throw new RuntimeException("Refresh Token inválido");
		}
		
		if (expirou(refreshToken)) {
			throw new RuntimeException("Refresh Token expirado");
		}
		
		String token = jwtService.gerarToken(refreshToken.getUsuario());
		
		refreshToken.setToken(token);
		refreshToken.setExpiracao(expiracao());
		
		return new JWTDTO(refreshToken.getUsuario().getId(), refreshToken.getToken(), refreshToken.getUuid());
	}
	
	private boolean expirou(RefreshToken refreshToken) {
		if (refreshToken.getExpiracao().isBefore(LocalDateTime.now())) {
			return true;
		}
		
		return false;
	}
	
	private LocalDateTime expiracao() {
		return LocalDateTime.now().plusMinutes(15);
	}
}
