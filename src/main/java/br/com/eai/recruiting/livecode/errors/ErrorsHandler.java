package br.com.eai.recruiting.livecode.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ErrorsHandler {
    @ExceptionHandler({InvalidRequest.class})
    public ResponseEntity<DefaultError> handleInvalidRequest(InvalidRequest ex, WebRequest request) {
        final var error = new DefaultError();
        error.setMessage(ex.getErrorCause().getMessage());
        error.setCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(error.getCode()).body(error);
    }
}
