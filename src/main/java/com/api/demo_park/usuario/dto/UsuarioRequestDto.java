package com.api.demo_park.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioRequestDto {

    @NotBlank(message = "O nome não pode ser vazio")
    private String nameDto;

    @NotBlank
    @Email(message = "O email deve ser válido")
    private String emailDto;

    @NotBlank(message = "A senha não deve ser vazia")
    @Size(min = 6, max = 15, message = "A senha deve ter entre 6 e 15 caracteres")
    private String passwordDto;

    @NotBlank
    private String roleDto;

}
