package io.github.raphaelrighetti.memotecaapi.exception;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.raphaelrighetti.memotecaapi.exception.dto.ErroGenericoDTO;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorException {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Void> entityNotFoundException() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ErroValidacaoDTO>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
		List<FieldError> fieldErrors = e.getFieldErrors();
		
		List<ErroValidacaoDTO> dtos = fieldErrors.stream().map(ErroValidacaoDTO::new).toList();
		
		return ResponseEntity.badRequest().body(dtos);
	}
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ErroGenericoDTO> sqlException(SQLException e) {
		return ResponseEntity.badRequest().body(new ErroGenericoDTO(e.getMessage()));
	}
	
	@ExceptionHandler(AcessoProibidoException.class)
	public ResponseEntity<Void> acessoProibidoException() {
		return ResponseEntity.status(403).build();
	}
	
	@ExceptionHandler(RefreshTokenException.class)
	public ResponseEntity<ErroGenericoDTO> refreshTokenException(RefreshTokenException e) {
		return ResponseEntity.status(403).body(new ErroGenericoDTO(e.getMessage()));
	}
	
	private record ErroValidacaoDTO(
			String campo,
			String mensagem
	) {
		public ErroValidacaoDTO(FieldError fieldError) {
			this(fieldError.getField(), fieldError.getDefaultMessage());
		}
	}
}
