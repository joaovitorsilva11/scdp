package br.gov.cofen.scdp.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequisicaoDetalheDTO {
    private Long id;
    private String tipo;
    private String chaveCompleta;
    private Object dados;
}

