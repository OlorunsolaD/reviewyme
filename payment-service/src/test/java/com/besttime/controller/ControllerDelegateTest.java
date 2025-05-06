package com.besttime.controller;

import com.besttime.model.GetUserPaymentsResponse;
import com.besttime.model.InitiatePaymentRequest;
import com.besttime.model.InitiatePaymentResponse;
import com.besttime.model.VerifyPaymentResponse;
import com.besttime.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerDelegateTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private ControllerDelegate controllerDelegate;

    private InitiatePaymentRequest paymentRequest;
    private InitiatePaymentResponse paymentResponse;
    private VerifyPaymentResponse verifyPaymentResponse;
    private List<GetUserPaymentsResponse> userPayments;

    @BeforeEach
    void setUp() {
        paymentRequest = new InitiatePaymentRequest();
        paymentRequest.setUserId("12345");

        paymentResponse = new InitiatePaymentResponse();
        verifyPaymentResponse = new VerifyPaymentResponse();
        userPayments = Collections.singletonList(new GetUserPaymentsResponse());

        // Use lenient stubbing to prevent unnecessary stubbing exceptions
        Mockito.lenient().when(paymentService.initiatePayment(any(InitiatePaymentRequest.class))).thenReturn(paymentResponse);
        Mockito.lenient().when(paymentService.verifyPayment(anyString())).thenReturn(verifyPaymentResponse);
        Mockito.lenient().when(paymentService.getUserPayments(anyString())).thenReturn(userPayments);
    }

    @Test
    void testInitiatePayment() {
        ResponseEntity<InitiatePaymentResponse> response = controllerDelegate.initiatePayment(paymentRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(paymentResponse, response.getBody());
        verify(paymentService, times(1)).initiatePayment(any(InitiatePaymentRequest.class));
    }

    @Test
    void testVerifyPayment() {
        ResponseEntity<VerifyPaymentResponse> response = controllerDelegate.verifyPayment("txn123");
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(verifyPaymentResponse, response.getBody());
        verify(paymentService, times(1)).verifyPayment(anyString());
    }

    @Test
    void testGetUserPayments() {
        ResponseEntity<List<GetUserPaymentsResponse>> response = controllerDelegate.getUserPayments("12345");
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(userPayments, response.getBody());
        verify(paymentService, times(1)).getUserPayments(anyString());
    }
}
