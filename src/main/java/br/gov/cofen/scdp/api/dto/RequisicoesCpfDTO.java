package br.gov.cofen.scdp.api.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class RequisicoesCpfDTO {

    private String cpf;
    private List<RequisicaoResumoDTO> requisicoes;
}
