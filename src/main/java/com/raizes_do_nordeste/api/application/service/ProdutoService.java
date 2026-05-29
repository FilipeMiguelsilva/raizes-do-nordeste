package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.entity.Produto;
import com.raizes_do_nordeste.api.infrastructure.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto cadastrar(Produto produto) {

        if (produtoRepository.findByNome(produto.getNome()).isPresent()) {
            throw new RuntimeException("Produto já cadastrado");
        }

        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    // listar produtos ativos no sistema
    public List<Produto> listarAtivos() {

        return produtoRepository.findByStatus(true);
    }

    //primeiro valida se existe e id, pra depois deleter com sucesso
    public void deletar(Long id) {

        buscarPorId(id);

        produtoRepository.deleteById(id);
    }


    // verifica se produto existe e atualiza os campos
    public Produto atualizar(Long id, Produto produtoAtualizado) {

        Produto produto = buscarPorId(id);

        produto.setNome(produtoAtualizado.getNome());
        produto.setStatus(produtoAtualizado.getStatus());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setCategoria(produtoAtualizado.getCategoria());

        return produtoRepository.save(produto);
    }

    // altera status do produto
    public Produto ativar(Long id) {
        Produto produto = buscarPorId(id);
        produto.setStatus(true);

        return produtoRepository.save(produto);
    }

    public Produto desativar(Long id) {
        Produto produto = buscarPorId(id);
        produto.setStatus(false);

        return produtoRepository.save(produto);
    }
}