package openapi;

import com.api.demo_park.usuario.dto.UsuarioFiltroDto;
import com.api.demo_park.usuario.dto.UsuarioRequestDto;
import com.api.demo_park.usuario.dto.UsuarioRequestNovaSenhaDto;
import com.api.demo_park.usuario.dto.UsuarioResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    @ApiError422
    @ApiError409
    ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioRequestDto usuarioDto);



    @Operation(summary = "Criar um usuário por ID", description = "Recurso para buscar um usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UsuarioResponseDto.class))),
            })
    @ApiError422
    @ApiError404
    ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable("id") Long Id);


    @Operation(summary = "A atualização foi bem-sucedida, senha atualizada", description = "Recurso para atualizar senha do usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UsuarioResponseDto.class))),
            })
    @ApiError422
    @ApiError404
    ResponseEntity<UsuarioResponseDto> atualizarSenhaId(@PathVariable("id") Long id, @Valid @RequestBody UsuarioRequestNovaSenhaDto usuarioRequestNovaSenhaDto);


    @Operation(summary = "Buscar todos os usuarios páginado", description = "Recurso para buscar todos os usuários páginado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UsuarioResponseDto.class))),
            })
    @ApiError422
    @ApiError404
    ResponseEntity<Page<UsuarioResponseDto>> listarUsuario(
            @RequestBody UsuarioFiltroDto usuarioDto,
            @PageableDefault(size = 10) Pageable page);

}
