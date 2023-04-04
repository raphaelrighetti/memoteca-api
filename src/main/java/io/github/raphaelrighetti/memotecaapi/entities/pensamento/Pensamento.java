package io.github.raphaelrighetti.memotecaapi.entities.pensamento;

import io.github.raphaelrighetti.memotecaapi.entities.pensamento.dto.PensamentoAtualizacaoDTO;
import io.github.raphaelrighetti.memotecaapi.entities.usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "pensamento")
@Entity(name = "Pensamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "conteudo", "autoria"})
public class Pensamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String conteudo;
	
	@NotNull
	private String autoria;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Modelo modelo;
	
	@NotNull
	private Boolean favorito;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	public void atualizar(PensamentoAtualizacaoDTO dados) {
		conteudo = dados.conteudo();
		autoria = dados.autoria();
		modelo = dados.modelo();
		favorito = dados.favorito();
	}
}
