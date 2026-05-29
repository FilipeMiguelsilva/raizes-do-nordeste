package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.entity.Cardapio;
import com.raizes_do_nordeste.api.infrastructure.repository.CardapioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CardapioService {

    private final CardapioRepository cardapioRepository;

    public CardapioService(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    public Cardapio buscarPorId(Long id) {
        return cardapioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id não encontrado"));
    }

    //ao cadastrar produto, caso produto já cadastrado ele avisara
    public Cardapio cadastrar(Cardapio cardapio) {

        if (cardapioRepository.findByUnidadeIdAndProdutoId(
                cardapio.getUnidade().getId(),
                cardapio.getProduto().getId()).isPresent()) {
            throw new RuntimeException("Produto já cadastrado");
        }

        return cardapioRepository.save(cardapio);
    }

    public List<Cardapio> listarPorUnidade(Long unidadeId) {

        return cardapioRepository.findByUnidadeId(unidadeId);
    }


    // altera status do cardapio
    public Cardapio ativar(Long id) {
        Cardapio cardapio = buscarPorId(id);
        cardapio.setStatus(true);

        return cardapioRepository.save(cardapio);
    }

    public Cardapio desativar(Long id) {
        Cardapio cardapio = buscarPorId(id);
        cardapio.setStatus(false);

        return cardapioRepository.save(cardapio);
    }

}

