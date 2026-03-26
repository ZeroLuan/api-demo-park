package openapi;

import com.api.demo_park.exception.config.ErrorMessageResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE}) // Informa onde pode ser usado a anotação. Posso usar em métodos ou na classe toda
@Retention(RetentionPolicy.RUNTIME) // Informa em qual situação vai executar a anotação. O Swagger consegue ler enquanto o app roda
@ApiResponse(
        responseCode = "409",
        description = "Requisição inválida",
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorMessageResponse.class)
        )
)
public @interface ApiError409 { // Quando você vê @interface, você não está criando uma "Interface comum" (como UsuarioDoc), você está criando uma Anotação Personalizada.
}
