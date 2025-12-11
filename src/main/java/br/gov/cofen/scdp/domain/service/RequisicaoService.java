package br.gov.cofen.scdp.domain.service;

import br.gov.cofen.scdp.api.dto.RequisicaoDetalheDTO;
import br.gov.cofen.scdp.api.dto.RequisicaoDiariaDTO;
import br.gov.cofen.scdp.api.dto.RequisicaoPassagemDTO;
import br.gov.cofen.scdp.api.dto.RequisicaoResumoDTO;
import br.gov.cofen.scdp.api.dto.RequisicoesCpfDTO;
import br.gov.cofen.scdp.domain.entity.RequisicaoDiariaEntity;
import br.gov.cofen.scdp.domain.entity.RequisicaoEntity;
import br.gov.cofen.scdp.domain.entity.RequisicaoPassagemEntity;
import br.gov.cofen.scdp.domain.entity.UsuarioEntity;
import br.gov.cofen.scdp.domain.repository.RequisicaoDiariaRepository;
import br.gov.cofen.scdp.domain.repository.RequisicaoPassagemRepository;
import br.gov.cofen.scdp.domain.repository.RequisicaoRepository;
import br.gov.cofen.scdp.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequisicaoService {

    private final UsuarioRepository usuarioRepository;
    private final RequisicaoRepository requisicaoRepository;
    private final RequisicaoDiariaRepository diariaRepository;
    private final RequisicaoPassagemRepository passagemRepository;

    // ----------------------------
    // Endpoint de CPF (novo formato)
    // ----------------------------
    public RequisicoesCpfDTO listarRequisicoesPorCpfComWrapper(String cpf) {

        UsuarioEntity usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<RequisicaoResumoDTO> requisicoes = requisicaoRepository.findByUsuarioId(usuario.getId()).stream()
                .filter(req -> req.getStatus() != 3) // tb_requisicao.situacao_id <> 3
                .map(req -> {

                    Optional<RequisicaoDiariaEntity> diaria =
                            diariaRepository.findByRequisicaoId(req.getId());

                    Optional<RequisicaoPassagemEntity> passagem =
                            passagemRepository.findByRequisicaoId(req.getId());

                    // --- DIÁRIA ---
                    if (diaria.isPresent()) {
                        long situacao = diaria.get().getSituacao().getId();
                        if (situacao == 7 || situacao == 8 || situacao == 15) return null;

                        return RequisicaoResumoDTO.builder()
                                .id(req.getId())
                                .dados(RequisicaoResumoDTO.DadosDTO.builder()
                                        .tipo("DIARIA")
                                        .status(diaria.get().getSituacao().getDescricao())
                                        .chave(diaria.get().getChave())
                                        .build())
                                .build();
                    }

                    // --- PASSAGEM ---
                    if (passagem.isPresent()) {
                        long situacao = passagem.get().getSituacao().getId();
                        if (situacao == 6 || situacao == 7) return null;

                        return RequisicaoResumoDTO.builder()
                                .id(req.getId())
                                .dados(RequisicaoResumoDTO.DadosDTO.builder()
                                        .tipo("PASSAGEM")
                                        .status(passagem.get().getSituacao().getDescricao())
                                        .chave(passagem.get().getChave())
                                        .build())
                                .build();
                    }

                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        return RequisicoesCpfDTO.builder()
                .cpf(cpf)
                .requisicoes(requisicoes)
                .build();
    }

    // ----------------------------
    // Endpoint de detalhes por ID
    // ----------------------------
    public RequisicaoDetalheDTO detalhes(Long id) {

        RequisicaoEntity req = requisicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requisição não encontrada"));

        Optional<RequisicaoDiariaEntity> diaria = diariaRepository.findByRequisicaoId(id);
        Optional<RequisicaoPassagemEntity> passagem = passagemRepository.findByRequisicaoId(id);

        String tipo;
        Object dados;
        String chaveCompleta;

        if (diaria.isPresent()) {
            tipo = "DIARIA";
            dados = mapDiaria(diaria.get());
            chaveCompleta = diaria.get().getChave();

        } else if (passagem.isPresent()) {
            tipo = "PASSAGEM";
            dados = mapPassagem(passagem.get());
            chaveCompleta = passagem.get().getChave();

        } else {
            throw new RuntimeException("Requisição sem tipo associado");
        }

        return RequisicaoDetalheDTO.builder()
                .id(req.getId())
                .tipo(tipo)
                .chaveCompleta(chaveCompleta)
                .dados(dados)
                .build();
    }

    // ----------------------------
    // Mappers
    // ----------------------------
    private RequisicaoDiariaDTO mapDiaria(RequisicaoDiariaEntity e) {
        return RequisicaoDiariaDTO.builder()
                .requisicao(e.getRequisicao().getId().toString())
                .situacao(e.getSituacao().getDescricao())
                .chave(e.getChave())
                .dataInicio(e.getDataInicio())
                .dataFim(e.getDataFim())
                .descricao(e.getDescricao())
                .build();
    }

    private RequisicaoPassagemDTO mapPassagem(RequisicaoPassagemEntity e) {
        return RequisicaoPassagemDTO.builder()
                .requisicao(e.getRequisicao().getId().toString())
                .situacao(e.getSituacao().getDescricao())
                .chave(e.getChave())
                .dataViagem(e.getDataViagem())
                .motivo(e.getMotivo())
                .build();
    }
}
