package io.github.raphaelrighetti.memotecaapi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.raphaelrighetti.memotecaapi.entities.pensamento.Pensamento;

public interface PensamentoRepository extends JpaRepository<Pensamento, Long> {

	Page<Pensamento> findByFavoritoTrue(Pageable pageable);
	
	@Query("""
			select p from Pensamento p
			where p.usuario.id = :usuarioId
			""")
	Page<Pensamento> pensamentosDoUsuario(Long usuarioId, Pageable pageable);
	
	@Query("""
			select p from Pensamento p
			where p.usuario.id = :usuarioId
			and p.favorito = true
			""")
	Page<Pensamento> pensamentosFavoritosDoUsuario(Long usuarioId, Pageable pageable);
	
	@Query(value = """
			select * from pensamento
			where usuario_id = ?1
			and to_tsvector(conteudo || ' ' || autoria || ' ' || modelo)
			@@
			to_tsquery(concat(regexp_replace(trim(?2), '\\W+', ':* & '), ':*'));
			""", nativeQuery = true)
	Page<Pensamento> pensamentosDoUsuarioPorFiltro(Long usuarioId, String filtro, Pageable pageable);
	
	@Query(value = """
			select * from pensamento
			where pensamento.usuario_id = ?1
			and pensamento.favorito = true
			and to_tsvector(conteudo || ' ' || autoria || ' ' || modelo)
			@@
			to_tsquery(concat(regexp_replace(trim(?2), '\\W+', ':* & '), ':*'));
			""", nativeQuery = true)
	Page<Pensamento> pensamentosFavoritosDoUsuarioPorFiltro(Long usuarioId, String filtro, Pageable pageable);
	
	@Query(value = """
			select * from pensamento 
			where to_tsvector(conteudo || ' ' || autoria || ' ' || modelo)
			@@
			to_tsquery(concat(regexp_replace(trim(?1), '\\W+', ':* & '), ':*'));
			""", nativeQuery = true)
	Page<Pensamento> pensamentosPorFiltro(String filtro, Pageable pageable);
	
	@Query(value = """
			select * from pensamento 
			where to_tsvector(conteudo || ' ' || autoria || ' ' || modelo)
			@@
			to_tsquery(concat(regexp_replace(trim(?1), '\\W+', ':* & '), ':*'))
			and pensamento.favorito = true;
			""", nativeQuery = true)
	Page<Pensamento> pensamentosFavoritosPorFiltro(String filtro, Pageable pageable);
}
