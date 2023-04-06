package io.github.raphaelrighetti.memotecaapi.entities.token.dto;

public record JWTDTO(
		Long usuarioId,
		String token,
		String refreshToken
) {
	
}
