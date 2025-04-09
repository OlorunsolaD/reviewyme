package com.besttime.controller;

import com.besttime.PaymentControllerApiDelegate;
import com.besttime.model.GetUserPaymentsResponse;
import com.besttime.model.InitiatePaymentRequest;
import com.besttime.model.InitiatePaymentResponse;
import com.besttime.model.VerifyPaymentResponse;
import com.besttime.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ControllerDelegate implements PaymentControllerApiDelegate {

    private final PaymentService paymentServiceImpl;

    @Override
    public ResponseEntity<InitiatePaymentResponse> initiatePayment(InitiatePaymentRequest initiatePaymentRequest) {
        log.info("received initiate payment request for user: {}", initiatePaymentRequest.getUserId());
        return new ResponseEntity<>(paymentServiceImpl.initiatePayment(initiatePaymentRequest), HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<VerifyPaymentResponse> verifyPayment(String transactionId) {
        log.info("received verify payment request with transaction id: {}", transactionId);
        VerifyPaymentResponse verifyPaymentResponse = paymentServiceImpl.verifyPayment(transactionId);
        return new ResponseEntity<>(verifyPaymentResponse, HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<List<GetUserPaymentsResponse>> getUserPayments(String userId) {
        log.info("received get user payments for user id: {}", userId);
        List<GetUserPaymentsResponse> userPayments = paymentServiceImpl.getUserPayments(userId);
        return new ResponseEntity<>(userPayments, HttpStatusCode.valueOf(200));
    }
}
