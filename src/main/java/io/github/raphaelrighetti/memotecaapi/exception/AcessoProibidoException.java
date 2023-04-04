package io.github.raphaelrighetti.memotecaapi.exception;

public class AcessoProibidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AcessoProibidoException() {
		super();
	}
	
	public AcessoProibidoException(String mensagem) {
		super(mensagem);
	}
}
