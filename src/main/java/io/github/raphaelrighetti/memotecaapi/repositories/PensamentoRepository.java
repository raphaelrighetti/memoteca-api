package io.github.raphaelrighetti.memotecaapi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.raphaelrighetti.memotecaapi.entities.pensamento.Pensamento;

public interface PensamentoRepository extends JpaRepository<Pensamento, Long> {

	Page<Pensamento> findByFavoritoTrue(Pageable pageable);
	
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
