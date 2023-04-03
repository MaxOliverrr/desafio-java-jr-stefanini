package com.maxoliver.desafiojavajrstefanini.handler;

import com.maxoliver.desafiojavajrstefanini.exceptions.BadRequestException;
import com.maxoliver.desafiojavajrstefanini.exceptions.EntityInUseException;
import com.maxoliver.desafiojavajrstefanini.exceptions.NotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> NotFoundException(Exception ex, WebRequest request){
        var NotFound = HttpStatus.NOT_FOUND;
        var responseBody = ResponseBody.builder()
                .title("Not Found")
                .detail(ex.getMessage())
                .type("http://localhost:8080/stefannini/library/authors/not-found-exception")
                .status(NotFound.value()).build();
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), NotFound, request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> BadRequestException(Exception ex, WebRequest request){
        var BadRequest = HttpStatus.BAD_REQUEST;
        var responseBody = ResponseBody.builder()
                .title("Bad Request")
                .detail(ex.getMessage())
                .type("http://localhost:8080/stefannini/library/authors/bad-request-exception")
                .status(BadRequest.value()).build();
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), BadRequest, request);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> ValidationException(Exception ex, WebRequest request){
        var BadRequest = HttpStatus.BAD_REQUEST;
        var responseBody = ResponseBody.builder()
                .title("Bad Request")
                .detail("Insira um país válido")
                .type("http://localhost:8080/stefannini/library/authors/bad-request-exception")
                .status(BadRequest.value()).build();
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), BadRequest, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> DataIntegrityViolationException(Exception ex, WebRequest request){
        var BadRequest = HttpStatus.BAD_REQUEST;
        var responseBody = ResponseBody.builder()
                .title("Bad Request")
                .detail(ex.getMessage())
                .type("http://localhost:8080/stefannini/library/authors/bad-request-exception")
                .status(BadRequest.value()).build();
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), BadRequest, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> EntityInUseException(Exception ex, WebRequest request){
        var BadRequest = HttpStatus.CONFLICT;
        var responseBody = ResponseBody.builder()
                .title("Conflict")
                .detail(ex.getMessage())
                .type("http://localhost:8080/stefannini/library/authors/entity-in-use-exception")
                .status(BadRequest.value()).build();
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), BadRequest, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String detailMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        var responseBody = ResponseBody.builder()
                .title("Bad Request")
                .detail(detailMessage)
                .type("http://localhost:8080/stefannini/library/authors/bad-request-exception")
                .status(status.value())
                .build();

        return handleExceptionInternal(ex, responseBody, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var responseBody = ResponseBody.builder()
                .title("Bad Request")
                .detail("O corpo da requisição é obrigatorio e apresenta erros")
                .type("http://localhost:8080/stefannini/library/authors/bad-request-exception")
                .status(status.value()).build();

        return handleExceptionInternal(ex, responseBody, headers, status, request);
    }
}
