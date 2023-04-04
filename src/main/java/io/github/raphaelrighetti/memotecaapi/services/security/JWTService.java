package io.github.raphaelrighetti.memotecaapi.services.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JWTService {

	@Value("${jwt.secret}")
	private String secret;
	
	public String gerarToken(UserDetails usuario) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		return JWT.create()
				.withSubject(usuario.getUsername())
				.withIssuer("Memoteca API")
				.withExpiresAt(expiracao())
				.sign(algorithm);
	}
	
	public String getSubject(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		return JWT.require(algorithm)
				.withIssuer("Memoteca API")
				.build()
				.verify(token)
				.getSubject();
	}
	
	private Instant expiracao() {
		return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
	}
}
