package com.mieker.ifpr.shelfie.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.resource.NoResourceFoundException;

import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler extends Throwable {
    @ExceptionHandler({Exception.class, DataIntegrityViolationException.class})
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;
        String message = exception.getMessage();

        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", message);
            return errorDetail;
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "A conta está bloqueada");
            return errorDetail;
        }

        if (exception instanceof AccessForbiddenException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", message);
            return errorDetail;
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "O token é inválido.");
            return errorDetail;
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "O token expirou.");
            return errorDetail;
        }

        if (exception instanceof DataIntegrityViolationException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), exception.getMessage());
            errorDetail.setProperty("description", "Ocorreu um erro de violação de integridade de dados.");
            return errorDetail;
        }

        if (exception instanceof ExceededPageLimitException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), exception.getMessage());
            errorDetail.setProperty("description", "A quantidade de páginas adicionadas é maior que a quantidade total de páginas do livro.");
            return errorDetail;
        }

        if (exception instanceof MethodArgumentTypeMismatchException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), exception.getMessage());
            errorDetail.setProperty("description", "O parâmetro passado é inválido.");
            return errorDetail;
        }

        if (exception instanceof BadRequestException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), exception.getMessage());
            errorDetail.setProperty("description", "Bad request.");
            return errorDetail;
        }

        if (exception instanceof NotFoundException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getMessage());
            errorDetail.setProperty("description", message);
            return errorDetail;
        }

        if (exception instanceof IdNotFoundException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getMessage());
            errorDetail.setProperty("description", "Esse id não existe.");
            return errorDetail;
        }

        if (exception instanceof NoResourceFoundException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getMessage());
            errorDetail.setProperty("description", "Rota não existe.");
            return errorDetail;
        }

        // Tratamento padrão para exceções desconhecidas
        errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
        errorDetail.setProperty("description", "Erro desconhecido.");

        return errorDetail;
    }
}
