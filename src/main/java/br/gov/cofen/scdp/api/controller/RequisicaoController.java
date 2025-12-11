package br.gov.cofen.scdp.api.controller;

import br.gov.cofen.scdp.api.dto.RequisicaoDetalheDTO;
import br.gov.cofen.scdp.api.dto.RequisicaoResumoDTO;
import br.gov.cofen.scdp.domain.service.RequisicaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/requisicoes")
@RequiredArgsConstructor
public class RequisicaoController {

    private final RequisicaoService service;

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<RequisicaoResumoDTO>> listar(@PathVariable String cpf) {
        validarCpf(cpf);
        return ResponseEntity.ok(service.listarRequisicoesPorCpf(cpf));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RequisicaoDetalheDTO> detalhes(@PathVariable Long id) {
        return ResponseEntity.ok(service.detalhes(id));
    }

    private void validarCpf(String cpf) {
        if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter 11 dígitos numéricos.");
        }
    }
}
