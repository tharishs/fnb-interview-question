package za.co.fnb.interview.util.aspect.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import za.co.fnb.interview.model.ErrorResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> genericError(Exception ex) {
        return new ResponseEntity<>(convertGenericError(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse convertGenericError(Exception ex) {

        ErrorResponse errorResponse = new ErrorResponse();

        String errorMessage = ex.getMessage() != null ? ex.getMessage() : ex.toString();
        errorResponse.setMessage(errorMessage);
        errorResponse.setId(UUID.randomUUID().toString());
        errorResponse.setTime(LocalDateTime.now());

        log.error(errorResponse.toString(), ex);

        return errorResponse;
    }
}
