package io.github.raphaelrighetti.memotecaapi.entities.token.dto;

public record JWTDTO(
		String token,
		String refreshToken
) {
	
}
