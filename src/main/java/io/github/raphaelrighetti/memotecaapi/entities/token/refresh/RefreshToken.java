package io.github.raphaelrighetti.memotecaapi.entities.token.refresh;

import java.time.LocalDateTime;

import io.github.raphaelrighetti.memotecaapi.entities.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "refresh_token")
@Entity(name = "Refresh")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "uuid"})
public class RefreshToken {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	@NotNull
	private String uuid;
	
	@NotNull
	private String token;
	
	@NotNull
	private LocalDateTime expiracao;
	
	@OneToOne
	@JoinColumn(name = "usuario_id")
	@NotNull
	private Usuario usuario;
}
