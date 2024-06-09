package br.edu.ies.aps8.config;

import br.edu.ies.aps8.dto.StandardResponse;
import br.edu.ies.aps8.dto.StandardResponseError;
import br.edu.ies.aps8.exception.InvalidCredentialsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardResponseError> handleIllegalArgumentException(HttpServletRequest httpServletRequest, IllegalArgumentException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(StandardResponseError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .error(ex.getMessage())
                        .path(httpServletRequest.getServletPath())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Validation failed");
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(response);
    }

    @ExceptionHandler({InvalidCredentialsException.class})
    public ResponseEntity<StandardResponse> handler(InvalidCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(StandardResponse.builder()
                        .message(e.getMessage())
                        .build());
    }

}
