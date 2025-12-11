package br.gov.cofen.scdp.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequisicaoResumoDTO {
    private String chave;
    private String tipo;
    private String status;
}

