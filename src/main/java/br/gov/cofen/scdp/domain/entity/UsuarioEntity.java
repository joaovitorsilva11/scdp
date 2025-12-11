package br.gov.cofen.scdp.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(schema = "public", name = "tb_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntity {
    @Id
    @Column(name = "nr_id")
    private Long id;

    @Column(name = "vc_cpf", nullable = false, length = 11)
    private String cpf;

    @Column(name = "vc_nome")
    private String nome;


}
