package br.gov.cofen.scdp.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RequisicaoPassagemDTO {
    private String requisicao;
    private LocalDateTime dataViagem;
    private String chave;
    private String situacao;
    private String motivo;
}

