package io.github.raphaelrighetti.memotecaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.raphaelrighetti.memotecaapi.entities.usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByUsername(String username);
}
