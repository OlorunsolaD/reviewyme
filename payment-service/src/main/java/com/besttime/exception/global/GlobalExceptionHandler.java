package com.besttime.exception.global;

import com.besttime.exception.PaymentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.besttime.constant.AppConstant.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorRepresentation> handleCustomRuntimeException(PaymentException ex) {
        log.error("Payment exception: {}", ex.getMessage());
        ErrorRepresentation error = new ErrorRepresentation(
                ex.getCode(),
                ex.getMessage(),
                ex.getReason()
        );
        if (error.getCode().equals(ERROR_CODE_5))
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRepresentation> handleGeneralException(Exception ex) {
        log.error("Exception: {}", ex.getMessage());
        ErrorRepresentation error = new ErrorRepresentation(
                ERROR_CODE_5,
                INTERNAL_ERROR,
                AN_UNEXPECTED_ERROR_OCCURRED
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
