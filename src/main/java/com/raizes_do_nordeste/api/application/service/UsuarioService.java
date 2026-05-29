package com.raizes_do_nordeste.api.application.service;

import com.raizes_do_nordeste.api.domain.entity.Usuario;
import com.raizes_do_nordeste.api.domain.enums.PerfilUsuario;
import com.raizes_do_nordeste.api.infrastructure.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository ,PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //ao cadastrar usuario, caso email já cadastrado ele avisara e tbm caso for null, perfiUsuario será cliente
    public Usuario cadastrar( Usuario usuario) {

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        if (usuario.getPerfil() == null) {
            usuario.setPerfil(PerfilUsuario.CLIENTE);
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {

        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }


    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // verifica se usuário existe e atualiza os campos
    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {

        Usuario usuario = buscarPorId(id);

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setStatus(usuarioAtualizado.getStatus());

        return usuarioRepository.save(usuario);
    }

    // altera status do usuario
    public Usuario ativar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setStatus(true);

        return usuarioRepository.save(usuario);
    }

    public Usuario desativar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setStatus(false);

        return usuarioRepository.save(usuario);
    }


}