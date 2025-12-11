package br.gov.cofen.scdp.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RequisicaoDiariaDTO {

    private String requisicao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String chave;
    private String situacao;
    private String descricao;
}

