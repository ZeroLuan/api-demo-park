package com.api.demo_park.usuario.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioResponseDto {

    private Long idDto;
    private String nameDto;
    private String emailDto;
    private String roleDto;

}
