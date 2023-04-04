package io.github.raphaelrighetti.memotecaapi.entities.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioTransactionalDTO(
		@NotBlank @Size(max = 20)
		String username,
		@NotBlank @Size(min = 8)
		String senha
) {

}
