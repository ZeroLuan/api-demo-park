package openapi;

import com.api.demo_park.exception.config.ErrorMessageResponse;
import com.api.demo_park.usuario.dto.UsuarioRequestDto;
import com.api.demo_park.usuario.dto.UsuarioResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Usuários", description = "Contém todas as operações relacionadas aos usuários do sistema.")
public interface UsuarioDoc {

    @Operation(summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UsuarioResponseDto.class))),
            })
    @ApiError422 // Anotação personalizada - @ApiError422 - para documentar o erro 422 de forma consistente em toda a API.
    @ApiError409 // Anotação personalizada - @ApiError409 - para documentar o erro 409 de forma consistente em toda a API.
    ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioRequestDto usuarioDto);



    @Operation(summary = "Criar um usuário por ID", description = "Recurso para buscar um usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UsuarioResponseDto.class))),
            })
    @ApiError422 // Anotação personalizada - @ApiError422 - para documentar o erro 422 de forma consistente em toda a API.
    @ApiError404 // Anotação personalizada - @ApiError409 - para documentar o erro 409 de forma consistente em toda a API.
    ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable("id") Long Id);




}
