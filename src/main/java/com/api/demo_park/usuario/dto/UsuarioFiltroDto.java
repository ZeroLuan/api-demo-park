package com.api.demo_park.usuario.dto;

import com.api.demo_park.usuario.entity.Usuario;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioFiltroDto {

    private Long idDto;
    private String nameDto;
    private String emailDto;

    public UsuarioFiltroDto(Usuario usuario) {
        this.idDto = usuario.getId();
        this.nameDto = usuario.getName();
        this.emailDto = usuario.getEmail();
    }

}
