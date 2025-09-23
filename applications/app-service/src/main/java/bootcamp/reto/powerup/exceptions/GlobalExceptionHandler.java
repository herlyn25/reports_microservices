package bootcamp.reto.powerup.exceptions;

import bootcamp.reto.powerup.dynamodb.exceptions.NoSourceResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSourceResourceException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleEmailAlreadyUsed(NoSourceResourceException ex) {
        Map<String, Object> body = createErrorResponse(
                ConstantsAppLayer.NO_FOUND_REPORT,
                ex.getMessage(),
                null
        );
        return Mono.just(new ResponseEntity<>(body, HttpStatus.NOT_FOUND));
    }

    private Map<String, Object> createErrorResponse(String error, String message, Object details) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ConstantsAppLayer.TIMESTAMP, Instant.now().toString());
        body.put(ConstantsAppLayer.ERROR, error);
        body.put(ConstantsAppLayer.MESSAGE, message);
        return body;
    }
}
