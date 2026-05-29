package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.entity.Unidade;
import com.raizes_do_nordeste.api.infrastructure.repository.UnidadeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;

    public UnidadeService(UnidadeRepository unidadeRepository) {
        this.unidadeRepository = unidadeRepository;
    }

    public Unidade cadastrar(Unidade unidade) {

        if (unidadeRepository.findByNome(unidade.getNome()).isPresent()) {
            throw new RuntimeException("Unidade já cadastrada");
        }

        return unidadeRepository.save(unidade);
    }

    public List<Unidade> listarTodos() {
        return unidadeRepository.findAll();
    }

    public Unidade buscarPorId(Long id) {
        return unidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));
    }

    // primeiro valida se existe e id, pra depois deleter com sucesso
    public void deletar(Long id) {

        buscarPorId(id);

        unidadeRepository.deleteById(id);
    }

    // listar unidades ativas no sistema
    public List<Unidade> listarAtivos() {

        return unidadeRepository.findByStatus(true);
    }

    // verifica se unidade existe e atualiza os campos
    public Unidade atualizar(Long id, Unidade unidadeAtualizada) {

        Unidade unidade = buscarPorId(id);

        unidade.setNome(unidadeAtualizada.getNome());
        unidade.setEndereco(unidadeAtualizada.getEndereco());
        unidade.setCidade(unidadeAtualizada.getCidade());
        unidade.setEstado(unidadeAtualizada.getEstado());
        unidade.setTelefone(unidadeAtualizada.getTelefone());
        unidade.setStatus(unidadeAtualizada.getStatus());

        return unidadeRepository.save(unidade);
    }

    // altera status da unidade, caso esteje em funcionamento ou não
    public Unidade ativar(Long id) {
        Unidade unidade = buscarPorId(id);
        unidade.setStatus(true);

        return unidadeRepository.save(unidade);
    }

    public Unidade desativar(Long id) {
        Unidade unidade = buscarPorId(id);
        unidade.setStatus(false);

        return unidadeRepository.save(unidade);
    }
}