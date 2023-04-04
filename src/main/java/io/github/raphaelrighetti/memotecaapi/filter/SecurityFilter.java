package io.github.raphaelrighetti.memotecaapi.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.raphaelrighetti.memotecaapi.entities.usuario.Usuario;
import io.github.raphaelrighetti.memotecaapi.repositories.UsuarioRepository;
import io.github.raphaelrighetti.memotecaapi.services.security.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		
		if (header != null) {
			String token = header.replace("Bearer ", "");
			
			Usuario usuario = usuarioRepository.findByUsername(jwtService.getSubject(token));
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}
}
