package io.github.raphaelrighetti.memotecaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.raphaelrighetti.memotecaapi.entities.token.refresh.RefreshToken;
import io.github.raphaelrighetti.memotecaapi.entities.usuario.Usuario;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	
	RefreshToken findByUuid(String uuid);
	
	RefreshToken findByUsuario(Usuario usuario);
}
