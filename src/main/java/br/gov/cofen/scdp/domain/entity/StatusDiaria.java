package br.gov.cofen.scdp.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_situacao_diaria", schema = "diarias")
@Data
public class StatusDiaria {

    @Id
    @Column(name = "nr_id")
    private long id;

    @Column(name = "vc_descricao")
    private String descricao;
}
