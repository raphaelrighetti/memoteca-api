package io.github.raphaelrighetti.memotecaapi.entities.pensamento.dto;

import io.github.raphaelrighetti.memotecaapi.entities.pensamento.Modelo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PensamentoCadastroDTO(
		@NotBlank
		String conteudo,
		@NotBlank
		String autoria,
		@NotNull
		Modelo modelo,
		@NotNull
		Long usuarioId
) {

}
