package com.api.demo_park.usuario.controller;

import com.api.demo_park.usuario.dto.UsuarioRequestDto;
import com.api.demo_park.usuario.dto.UsuarioResponseDto;
import com.api.demo_park.usuario.dto.UsuarioRequestNovaSenhaDto;
import com.api.demo_park.usuario.entity.Usuario;
import com.api.demo_park.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import openapi.UsuarioDoc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController implements UsuarioDoc {

    private final UsuarioService usuarioService;

    @PostMapping(path = "/criar")
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioRequestDto usuarioDto) {
        return ResponseEntity.status(201).body(usuarioService.salvar(usuarioDto));
    }

    @GetMapping(path = "/buscar/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable("id") Long Id) {
        return ResponseEntity.ok(usuarioService.buscarPorIdERetornaUsuarioDto(Id));
    }

    @PatchMapping(path = "/atualizar/senha/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizarSenhaId(@PathVariable("id") Long id, @Valid @RequestBody UsuarioRequestNovaSenhaDto usuarioRequestNovaSenhaDto) {
        return ResponseEntity.ok(usuarioService.atualizarSenhaPorId(id, usuarioRequestNovaSenhaDto));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> buscarPorId() {
        return ResponseEntity.ok(usuarioService.buscarTodos());
    }

}
