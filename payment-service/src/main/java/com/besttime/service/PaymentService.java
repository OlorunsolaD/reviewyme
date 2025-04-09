package com.besttime.service;

import com.besttime.model.GetUserPaymentsResponse;
import com.besttime.model.InitiatePaymentRequest;
import com.besttime.model.InitiatePaymentResponse;
import com.besttime.model.VerifyPaymentResponse;

import java.util.List;

public interface PaymentService {
    InitiatePaymentResponse initiatePayment(InitiatePaymentRequest initiatePaymentRequest);

    VerifyPaymentResponse verifyPayment(String transactionId);

    List<GetUserPaymentsResponse> getUserPayments(String userId);
}
