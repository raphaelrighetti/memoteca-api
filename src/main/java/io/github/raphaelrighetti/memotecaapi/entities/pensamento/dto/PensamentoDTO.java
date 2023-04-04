package io.github.raphaelrighetti.memotecaapi.entities.pensamento.dto;

import io.github.raphaelrighetti.memotecaapi.entities.pensamento.Modelo;
import io.github.raphaelrighetti.memotecaapi.entities.pensamento.Pensamento;

public record PensamentoDTO(
		Long id,
		String conteudo,
		String autoria,
		Modelo modelo,
		Boolean favorito,
		Long usuarioId
) {
	public PensamentoDTO(Pensamento pensamento) {
		this(
				pensamento.getId(),
				pensamento.getConteudo(), 
				pensamento.getAutoria(), 
				pensamento.getModelo(), 
				pensamento.getFavorito(), 
				pensamento.getUsuario().getId()	
		);
	}
}
