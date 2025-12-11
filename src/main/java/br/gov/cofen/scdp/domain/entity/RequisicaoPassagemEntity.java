package br.gov.cofen.scdp.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_requisicao_passagem", schema = "diarias")
@Data
public class RequisicaoPassagemEntity {
    @Id
    @Column(name = "nr_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requisicao_id", referencedColumnName = "nr_id")
    private RequisicaoEntity requisicao;

    @Column(name = "dt_data_viagem")
    private LocalDateTime dataViagem;

    @Column(name = "chave")
    private String chave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "situacao_id", referencedColumnName = "nr_id")
    private StatusPassagem situacao;

    @Column(name = "vc_motivo")
    private String motivo;
}
