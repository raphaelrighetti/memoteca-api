package io.github.raphaelrighetti.memotecaapi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.raphaelrighetti.memotecaapi.entities.pensamento.Pensamento;

public interface PensamentoRepository extends JpaRepository<Pensamento, Long> {
	
	@Query("""
			select p from Pensamento p
			where p.privado = false
			""")
	Page<Pensamento> pensamentos(Pageable pageable);
	
	@Query(value = """
			select * from pensamento 
			where privado = false
			and to_tsvector(conteudo || ' ' || autoria || ' ' || modelo)
			@@
			to_tsquery(concat(regexp_replace(trim(?1), '\\W+', ':* & '), ':*'));
			""", nativeQuery = true)
	Page<Pensamento> pensamentosPorFiltro(String filtro, Pageable pageable);
	
	@Query("""
			select p from Pensamento p
			where p.usuario.id = :usuarioId
			""")
	Page<Pensamento> pensamentosDoUsuario(Long usuarioId, Pageable pageable);
	
	@Query(value = """
			select * from pensamento
			where usuario_id = ?1
			and to_tsvector(conteudo || ' ' || autoria || ' ' || modelo)
			@@
			to_tsquery(concat(regexp_replace(trim(?2), '\\W+', ':* & '), ':*'));
			""", nativeQuery = true)
	Page<Pensamento> pensamentosDoUsuarioPorFiltro(Long usuarioId, String filtro, Pageable pageable);
	
	@Query("""
			select p from Pensamento p
			where p.usuario.id = :usuarioId
			and p.favorito = true
			""")
	Page<Pensamento> pensamentosFavoritosDoUsuario(Long usuarioId, Pageable pageable);
	
	@Query(value = """
			select * from pensamento
			where = usuario_id = ?1
			and favorito = true
			and to_tsvector(conteudo || ' ' || autoria || ' ' || modelo)
			@@
			to_tsquery(concat(regexp_replace(trim(?2), '\\W+', ':* & '), ':*'));
			""", nativeQuery = true)
	Page<Pensamento> pensamentosFavoritosDoUsuarioPorFiltro(Long usuarioId, String filtro, Pageable pageable);
}
