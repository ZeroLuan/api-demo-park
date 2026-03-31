package com.api.demo_park.usuario.service;

import com.api.demo_park.exception.GlobalException;
import com.api.demo_park.usuario.dto.UsuarioFiltroDto;
import com.api.demo_park.usuario.dto.UsuarioRequestDto;
import com.api.demo_park.usuario.dto.UsuarioResponseDto;
import com.api.demo_park.usuario.dto.UsuarioRequestNovaSenhaDto;
import com.api.demo_park.usuario.entity.Usuario;
import com.api.demo_park.usuario.mapper.UsuarioMapper;
import com.api.demo_park.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    @Transactional
    public UsuarioResponseDto salvar(UsuarioRequestDto usuarioDto) {
        // 1. Validação em tempo real padrão (Cobre 99.99% dos casos, poupando quebras no banco de dados)
        if(usuarioRepository.existsByName(usuarioDto.getNameDto())) {
            throw new GlobalException("O nome de usuário '" + usuarioDto.getNameDto() + "' já está em uso.");
        }

        Usuario usuario = usuarioMapper.toEntity(usuarioDto);
        usuario.setDataCriacao(LocalDateTime.now());

        // 2. Fallback de Segurança contra "Race Conditions" em alta simultaneidade (Cobre a chance do 0.01%)
        try {
            return usuarioMapper.toDto(usuarioRepository.saveAndFlush(usuario));
        } catch (DataIntegrityViolationException e) {
            // Se 2 transações passarem pelo IF exatamente ao mesmo tempo e o banco barrar o ultimo a chegar, a gente traduz o erro 500 com nossa mensagem!
            throw new GlobalException("O nome de usuário '" + usuarioDto.getNameDto() + "' já está em uso.");
        }
    }

    /* Aqui temos o meso codigo de salvar usuario, porém ele tá mais simples a nivel de um projeto pequeno o metodo a cima está projetado para um sistema de alta concorrência,
    onde muitos usuários podem tentar criar contas com o mesmo nome ao mesmo tempo. Ele tem uma validação em tempo real para evitar a maioria dos casos de duplicidade,
    e um fallback para garantir que mesmo em situações de alta simultaneidade, o sistema se comporte corretamente.
    Já o código abaixo é mais simples e direto, mas pode falhar em cenários de alta concorrência, onde duas ou mais transações podem passar pela validação ao mesmo tempo
    e tentar salvar usuários com o mesmo nome, resultando em um erro de banco de dados (DataIntegrityViolationException) que não é tratado.
     Em um projeto pequeno ou com baixa concorrência, o código simples pode ser suficiente, mas para sistemas maiores ou com muitos usuários, a abordagem mais robusta é recomendada é do método a cima.

    @Transactional
    public UsuarioResponseDto salvar(UsuarioRequestDto usuarioDto) {
        // Validamos no banco se já existe ALGUÉM com aquele nome
        if(usuarioRepository.existsByName(usuarioDto.getNameDto())) {
            throw new UsernameUniqueViolationException("O nome de usuário '" + usuarioDto.getNameDto() + "' já está em uso. Pro favor, escolha outro nome de usuário.");
        }
        Usuario usuario = usuarioMapper.toEntity(usuarioDto);
        usuario.setDataCriacao(LocalDateTime.now());
        // Aqui usamos o .save() normal!
        return usuarioMapper.toDto(usuarioRepository.save(usuario));
    }
     */

    @Transactional(readOnly = true)
    public UsuarioResponseDto buscarPorIdERetornaUsuarioDto(Long id) {
        return usuarioMapper.toDto(buscarPorIdERetornaUsuario(id));
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorIdERetornaUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new GlobalException("Usuário não encontrado com id: " + id));
    }

    @Transactional
    public UsuarioResponseDto atualizarSenhaPorId(Long id, UsuarioRequestNovaSenhaDto usuarioRequestNovaSenhaDto) {
        Usuario usuario = buscarPorIdERetornaUsuario(id);
        validaDadosUsuario(usuario, usuarioRequestNovaSenhaDto);
        buscarPorIdERetornaUsuario(id);
        usuario.setPassword(usuarioRequestNovaSenhaDto.getNovaSenha());
        usuario.setDataModificacao(LocalDateTime.now());
        return  usuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public Page<UsuarioResponseDto> buscarTodos(@Param("usuarioFiltro") UsuarioFiltroDto usuarioFiltro, Pageable page) {
        Page<Usuario> usuariosPaginado = usuarioRepository.filtrarUsuarioPaginado(usuarioFiltro, page);
        return usuariosPaginado.map(UsuarioResponseDto::new);
    }

    public void validaDadosUsuario(Usuario usuario, UsuarioRequestNovaSenhaDto usuarioRequestNovaSenhaDto) {
        if(usuario.getId() == null){
            throw new GlobalException("Id do usuário não pode ser nulo.");
        }
        if (usuarioRequestNovaSenhaDto == null) {
            throw new GlobalException("Dados da senha não informados.");
        }
        if(!usuario.getPassword().equals(usuarioRequestNovaSenhaDto.getSenhaAtual())){
            throw new GlobalException("A senha atual do usuario está incorreta.");
        }
        if(!usuarioRequestNovaSenhaDto.getNovaSenha().equals(usuarioRequestNovaSenhaDto.getConfirmacaoSenha())){
            throw new GlobalException("A nova senha e a confirmação de senha não coincidem.");
        }
    }
}
