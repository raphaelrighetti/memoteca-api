package io.github.raphaelrighetti.memotecaapi.entities.token.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDTO(
		@NotBlank
		String refreshToken
) {
	
}
