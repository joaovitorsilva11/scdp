package br.gov.cofen.scdp.domain.repository;

import br.gov.cofen.scdp.domain.entity.RequisicaoPassagemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequisicaoPassagemRepository extends JpaRepository<RequisicaoPassagemEntity, Long> {

    Optional<RequisicaoPassagemEntity> findByRequisicaoId(Long requisicaoId);
}
