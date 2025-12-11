package br.gov.cofen.scdp.domain.repository;

import br.gov.cofen.scdp.domain.entity.RequisicaoDiariaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequisicaoDiariaRepository extends JpaRepository<RequisicaoDiariaEntity, Long> {

    Optional<RequisicaoDiariaEntity> findByRequisicaoId(Long requisicaoId);
}
