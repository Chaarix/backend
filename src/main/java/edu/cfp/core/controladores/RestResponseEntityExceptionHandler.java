package edu.cfp.core.controladores;

import edu.cfp.core.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler  extends ResponseEntityExceptionHandler {
    @ExceptionHandler (BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequest(
            RuntimeException ex, WebRequest request) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("message", ex.getMessage());
            body.put("timesmap", LocalDateTime.now());
            body.put("error", HttpStatus.BAD_REQUEST.toString());

            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
