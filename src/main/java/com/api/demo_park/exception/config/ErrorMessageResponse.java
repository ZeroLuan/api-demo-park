package com.api.demo_park.exception.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;

@Getter @ToString
public class ErrorMessageResponse {

    // Caminho da exceção
    private String path;

    // Méto responsável pela exceção
    private String method;

    // Verbo http
    private int status;

    // Status do texto
    private String statusText;

    // Mensagem de erro
    private String errorMessage;

    // Lista de erros do BindingResult
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    public ErrorMessageResponse() {
    }

    public ErrorMessageResponse(HttpServletRequest request, HttpStatus httpStatus, String errorMessage) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = httpStatus.value();
        this.statusText = httpStatus.getReasonPhrase();
        this.errorMessage = errorMessage;
    }

    public ErrorMessageResponse(HttpServletRequest request, HttpStatus httpStatus, String errorMessage, BindingResult result) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = httpStatus.value();
        this.statusText = httpStatus.getReasonPhrase();
        this.errorMessage = errorMessage;
        addErrors(result);
        /*
        *
        * O "Trio de Ferro" da Validação
        * As Regras (No DTO): Você usa as anotações da Bean Validation (Hibernate Validator) para dizer o que é aceitável.
        * O Gatilho (No Controller): Você usa a anotação @Valid (ou @Validated) antes do parâmetro do objeto. Isso avisa ao Spring: "Ei, antes de me entregar esse objeto, valide-o!".
        * O Receptor (No Controller): O BindingResult logo em seguida, para capturar o que falhou.
        *
        * para fazer usodo Binding result então vc deve obedecer essas 3 regras aí.
        *
        */
    }

    public void addErrors(BindingResult result) {
        this.errors = new HashMap<>();
        for(FieldError fieldError : result.getFieldErrors()){
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

}
