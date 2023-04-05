package io.github.raphaelrighetti.memotecaapi.exception;

public class RefreshTokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RefreshTokenException() {
		super();
	}
	
	public RefreshTokenException(String mensagem) {
		super(mensagem);
	}
}
