package br.gov.cofen.scdp.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_situacao_passagem", schema = "diarias")
@Data
public class StatusPassagem {

    @Id
    @Column(name = "nr_id")
    private long id;

    @Column(name = "vc_descricao")
    private String descricao;
}
