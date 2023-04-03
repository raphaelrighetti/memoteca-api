package io.github.raphaelrighetti.memotecaapi.exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
	
	private record ErroValidacaoDTO(
			String campo,
			String mensagem
	) {
		public ErroValidacaoDTO(FieldError fieldError) {
			this(fieldError.getField(), fieldError.getDefaultMessage());
		}
	}
}
