package it.riccisi.babilonia.api.error;

import it.riccisi.babilonia.domain.exception.NoProjectFoundException;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private String traceId() {
        return MDC.get("traceId");
    }

    @ExceptionHandler(NoProjectFoundException.class)
    public ResponseEntity<ErrorResponse> handleProjectNotFound(
        NoProjectFoundException ex
    ) {
        ErrorResponse body = new ErrorResponse(
            ex.getErrorCode(),
            ex.getMessage(),
            traceId()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
        Exception ex
    ) {
        ErrorResponse body = new ErrorResponse(
            "INTERNAL_ERROR",
            "Unexpected server error",
            traceId()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
