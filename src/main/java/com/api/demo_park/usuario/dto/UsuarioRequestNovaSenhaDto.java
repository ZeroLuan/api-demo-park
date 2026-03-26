package com.api.demo_park.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioRequestNovaSenhaDto {

    @NotBlank(message = "A senha não deve ser vazia")
    @Size(min = 6, max = 15, message = "A senha deve ter entre 6 e 15 caracteres")
    private String senhaAtual;

    @NotBlank(message = "A senha não deve ser vazia")
    @Size(min = 6, max = 15, message = "A senha deve ter entre 6 e 15 caracteres")
    private String novaSenha;

    @NotBlank(message = "A senha não deve ser vazia")
    @Size(min = 6, max = 15, message = "A senha deve ter entre 6 e 15 caracteres")
    private String confirmacaoSenha;

}
