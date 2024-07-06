package com.mieker.ifpr.shelfie.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler extends Throwable {
    @ExceptionHandler({Exception.class, DataIntegrityViolationException.class})
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;

        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "O email ou a senha estão incorretos.");

            return errorDetail;
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "A conta está bloqueada");
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "Você não tem permissão para acessar este recurso.");
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "O token é inválido.");
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "O token expirou.");
        }

        if (exception instanceof DataIntegrityViolationException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), exception.getMessage());
            errorDetail.setProperty("description", "Ocorreu um erro de violação de integridade de dados.");
        }

        if (exception instanceof MethodArgumentTypeMismatchException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), exception.getMessage());
            errorDetail.setProperty("description", "O parâmetro passado é inválido.");
        }

        if (exception instanceof RuntimeException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Erro desconhecido.");
        }

        if (exception instanceof NotFoundException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getMessage());
            errorDetail.setProperty("description", "Não tem usuário associado ao livro.");
        }

        if (exception instanceof IdNotFoundException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getMessage());
            errorDetail.setProperty("description", "Esse id não existe.");
        }

        if (exception instanceof NoResourceFoundException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getMessage());
            errorDetail.setProperty("description", "Rota não existe.");
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Erro desconhecido.");
        }

        return errorDetail;
    }
}
