package br.gov.cofen.scdp.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequisicaoResumoDTO {

    private Long id;
    private DadosDTO dados;


    @Data
    @Builder
    public static class DadosDTO {
        private String tipo;
        private String status;
        private String chave;
    }
}


