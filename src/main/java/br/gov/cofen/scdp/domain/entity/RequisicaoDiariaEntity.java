package br.gov.cofen.scdp.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_requisicao_diaria", schema = "diarias")
@Data
public class RequisicaoDiariaEntity {
    @Id
    @Column(name = "nr_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requisicao_id", referencedColumnName = "nr_id")
    private RequisicaoEntity requisicao;

    @Column(name = "dt_data_inicio")
    private LocalDate dataInicio;

    @Column(name = "dt_data_fim")
    private LocalDate dataFim;

    @Column(name = "chave")
    private String chave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "situacao_id", referencedColumnName = "nr_id")
    private StatusDiaria situacao;

    @Column(name = "vc_especificar")
    private String descricao;
}

