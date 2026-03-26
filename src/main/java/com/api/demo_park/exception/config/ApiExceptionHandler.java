package com.api.demo_park.exception.config;

import com.api.demo_park.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // Isso torna a classe uma ouvinte, então quando uma exceção acontecer em qualquer lugar do código, ela vai ser capturada aqui e tratada.
public class ApiExceptionHandler {

    @ExceptionHandler(GlobalException.class) // Essa anotação indica que esse metodo vai lidar com as exceções do tipo UserNotFound, que são lançadas quando um usuário não é encontrado no banco de dados.
    public ResponseEntity<ErrorMessageResponse> globalException(
            RuntimeException ex,
            HttpServletRequest request){

        log.error("API ERROR: ", ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageResponse(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Essa anotação indica que esse metodo vai lidar com as exceções do tipo MethodArgumentNotValidException, que são lançadas quando a validação de um argumento falha. @Valid
    public ResponseEntity<ErrorMessageResponse> methodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request,
            BindingResult result){

        log.error("API ERROR: ", ex);

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageResponse(request, HttpStatus.UNPROCESSABLE_CONTENT, "Campo(s) Invalido(s)", result));
    }








    /* Outra opção de tratar o erro de validação, mas sem usar o BindingResult, ou seja, sem detalhar os erros de cada campo. Apenas pegando a primeira mensagem de erro e retornando ela.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> tratarErro(
            HttpServletRequest request,
            MethodArgumentNotValidException ex){

        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Erro de validação");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        mensagem,
                        request.getRequestURI()));
    }

    caso queria usar esse é só criar um ExceptionResponse com os campos
    private LocalDateTime timestamp;
    private Integer status;
    private String message;
    private String path;

     */


}
