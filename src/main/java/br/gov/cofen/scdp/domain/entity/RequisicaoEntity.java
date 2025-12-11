package br.gov.cofen.scdp.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(schema = "diarias", name = "tb_requisicao")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RequisicaoEntity {
    @Id
    @Column(name = "nr_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "nr_id")
    private UsuarioEntity usuario;

    @Column(name = "situacao_id")
    private int status;

    @Column(name = "dt_data_requisicao")
    private LocalDateTime dataRequisicao;
}
