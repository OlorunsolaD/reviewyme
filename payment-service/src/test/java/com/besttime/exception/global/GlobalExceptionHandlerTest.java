package com.besttime.exception.global;

import com.besttime.exception.PaymentException;
import com.besttime.constant.AppConstant;
import com.besttime.exception.global.GlobalExceptionHandler;
import com.besttime.exception.global.ErrorRepresentation;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleCustomRuntimeException_HappyPath_BadRequest() {
        PaymentException ex = new PaymentException(AppConstant.ERROR_CODE_4, "Invalid Payment", "Payment details are incorrect");

        ResponseEntity<ErrorRepresentation> response = globalExceptionHandler.handleCustomRuntimeException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(AppConstant.ERROR_CODE_4, response.getBody().getCode());
        assertEquals("Invalid Payment", response.getBody().getMessage());
        assertEquals("Payment details are incorrect", response.getBody().getReason());
    }

    @Test
    void testHandleCustomRuntimeException_HappyPath_InternalServerError() {
        PaymentException ex = new PaymentException(AppConstant.ERROR_CODE_5, "Server Error", "Payment processing failed due to server issue");

        ResponseEntity<ErrorRepresentation> response = globalExceptionHandler.handleCustomRuntimeException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(AppConstant.ERROR_CODE_5, response.getBody().getCode());
        assertEquals("Server Error", response.getBody().getMessage());
        assertEquals("Payment processing failed due to server issue", response.getBody().getReason());
    }

    @Test
    void testHandleGeneralException_UnhappyPath() {
        Exception ex = new Exception("Unexpected error occurred");

        ResponseEntity<ErrorRepresentation> response = globalExceptionHandler.handleGeneralException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(AppConstant.ERROR_CODE_5, response.getBody().getCode());
        assertEquals(AppConstant.INTERNAL_ERROR, response.getBody().getMessage());
        assertEquals(AppConstant.AN_UNEXPECTED_ERROR_OCCURRED, response.getBody().getReason());
    }
}
