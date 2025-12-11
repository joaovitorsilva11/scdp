package br.gov.cofen.scdp.domain.repository;

import br.gov.cofen.scdp.domain.entity.RequisicaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequisicaoRepository extends JpaRepository<RequisicaoEntity, Long> {

    List<RequisicaoEntity> findByUsuarioId(Long id);
}

