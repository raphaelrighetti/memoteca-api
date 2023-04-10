package io.github.raphaelrighetti.memotecaapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.raphaelrighetti.memotecaapi.entities.usuario.Usuario;
import io.github.raphaelrighetti.memotecaapi.entities.usuario.dto.UsuarioTransactionalDTO;
import io.github.raphaelrighetti.memotecaapi.exception.AcessoProibidoException;
import io.github.raphaelrighetti.memotecaapi.entities.usuario.dto.UsuarioDTO;
import io.github.raphaelrighetti.memotecaapi.repositories.UsuarioRepository;
import io.github.raphaelrighetti.memotecaapi.services.security.JWTService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTService jwtService;
	
	public UsuarioDTO registrar(UsuarioTransactionalDTO dados) {
		String senha = passwordEncoder.encode(dados.senha());
		
		Usuario usuario = new Usuario(null, dados.username(), senha);
		
		repository.save(usuario);
		
		return new UsuarioDTO(usuario);
	}
	
	public UsuarioDTO detalhar(String header, Long id) {
		Usuario usuario = repository.getReferenceById(id);
		
		return new UsuarioDTO(usuario);
	}
	
	public Page<UsuarioDTO> listar(Pageable pageable) {
		Page<UsuarioDTO> page = repository.findAll(pageable).map(UsuarioDTO::new);
		
		return page;
	}
	
	public UsuarioDTO atualizar(String header, Long id, UsuarioTransactionalDTO dados) {
		compararUsuarios(header, id);
		
		Usuario usuario = repository.getReferenceById(id);
		
		String senha = passwordEncoder.encode(dados.senha());
		
		usuario.setUsername(dados.username());
		usuario.setSenha(senha);
		
		return new UsuarioDTO(usuario);
	}
	
	public void excluir(String header, Long id) {
		if (!repository.existsById(id)) {
			throw new EntityNotFoundException();
		}
		
		compararUsuarios(header, id);
		
		repository.deleteById(id);
	}
	
	public void compararUsuarios(String header, Long id) {
		if (!repository.existsById(id)) {
			throw new EntityNotFoundException();
		}
		
		String token = header.replace("Bearer ", "");
		String username = jwtService.getSubject(token);
		
		Usuario usuarioDoToken = repository.findByUsername(username);
		Usuario usuario = repository.getReferenceById(id);
		
		if (usuarioDoToken != usuario) {
			throw new AcessoProibidoException();
		}
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByUsername(username);
	}
}
