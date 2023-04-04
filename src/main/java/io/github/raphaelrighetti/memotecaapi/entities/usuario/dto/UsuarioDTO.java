package io.github.raphaelrighetti.memotecaapi.entities.usuario.dto;

import io.github.raphaelrighetti.memotecaapi.entities.usuario.Usuario;

public record UsuarioDTO(
		Long id,
		String username
) {
	public UsuarioDTO(Usuario usuario) {
		this(usuario.getId(), usuario.getUsername());
	}
}
