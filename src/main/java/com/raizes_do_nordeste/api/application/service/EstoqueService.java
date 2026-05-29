package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.entity.Estoque;
import com.raizes_do_nordeste.api.infrastructure.repository.EstoqueRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;

    public EstoqueService (EstoqueRepository estoqueRepository){
        this.estoqueRepository = estoqueRepository;
    }

    //ao cadastrar produto, caso produto já cadastrado ele avisara
    public Estoque cadastrar(Estoque estoque) {

        if (estoqueRepository.findByUnidadeIdAndProdutoId(
                estoque.getUnidade().getId(),
                estoque.getProduto().getId()).isPresent()) {
            throw new RuntimeException("Produto já cadastrado");
        }
        return estoqueRepository.save(estoque);
    }

    public Estoque buscarPorId(Long id) {
        return estoqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id não encontrado"));
    }

    public Estoque buscarPorUnidadeEProduto(Long unidadeId, Long produtoId){
        return estoqueRepository.findByUnidadeIdAndProdutoId(unidadeId,produtoId)
                .orElseThrow(() -> new RuntimeException("Não encontrado"));
    }

    public Estoque adicionarQuantidade(Long id, int quantidade){

        Estoque estoque = buscarPorId(id);
        estoque.setQuantidade(estoque.getQuantidade() + quantidade);

        return estoqueRepository.save(estoque);
    }

    public Estoque removerPorUnidadeEProduto(Long unidadeId, Long produtoId, Integer quantidade) {

        Estoque estoque = buscarPorUnidadeEProduto(unidadeId, produtoId);

        if (estoque.getQuantidade() < quantidade) {
            throw new RuntimeException("Estoque insuficiente");
        }

        estoque.setQuantidade(estoque.getQuantidade() - quantidade);

        return estoqueRepository.save(estoque);
    }

    public void verificarDisponibilidade(Long unidadeId, Long produtoId, Integer quantidade) {
        Estoque estoque = estoqueRepository
                .findByUnidadeIdAndProdutoId(unidadeId, produtoId)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));

        if (estoque.getQuantidade() < quantidade) {
            throw new RuntimeException("Estoque insuficiente");
        }

    }

    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    public List<Estoque> listarPorUnidade(Long unidadeId) {
        return estoqueRepository.findByUnidadeId(unidadeId);
    }


}