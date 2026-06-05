package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.entity.Fidelizacao;
import com.raizes_do_nordeste.api.infrastructure.repository.FidelizacaoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FidelizacaoService {

    private final FidelizacaoRepository fidelizacaoRepository;

    public FidelizacaoService(FidelizacaoRepository fidelizacaoRepository) {
        this.fidelizacaoRepository = fidelizacaoRepository;
    }

    // cadastro do usuario que consentiu com LGPD
    public Fidelizacao cadastrar (Fidelizacao fidelizacao){

        if (!fidelizacao.getConsentLGPD()) {
            throw new RuntimeException("Usuário não aceitou os termos LGPD");
        }

        fidelizacao.setPontos(0);
        fidelizacao.setData(LocalDateTime.now());


        return fidelizacaoRepository.save(fidelizacao);

    }

    public List<Fidelizacao> listarTodos() {
        return fidelizacaoRepository.findAll();
    }

    public Fidelizacao buscarUsuarioId(Long id){

        return fidelizacaoRepository.findByUsuarioId(id)
                .orElseThrow(() -> new RuntimeException("id não encontrado"));
    }

    //adiciona os ponstos caso encontre o usuario cadastrado em fidelizacao
    public void  adicionarPontos(Long id, int pontos){

        fidelizacaoRepository.findByUsuarioId(id)
                .ifPresent(fidelizacao -> {

                    fidelizacao.setPontos(
                            fidelizacao.getPontos() + pontos
                    );

                    fidelizacaoRepository.save(fidelizacao);
                });

    }

    public Fidelizacao resgatarPontos(Long id, int pontos ) {

        Fidelizacao fidelizacao = buscarUsuarioId(id);
        if (fidelizacao.getPontos() < pontos) {
            throw new RuntimeException("Pontos insuficientes");
        }
        fidelizacao.setPontos(fidelizacao.getPontos()-pontos);

        return fidelizacaoRepository.save(fidelizacao);
    }


}
