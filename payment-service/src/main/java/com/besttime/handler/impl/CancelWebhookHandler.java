package com.besttime.handler.impl;

import com.besttime.entity.Payment;
import com.besttime.handler.WebhookHandler;
import com.besttime.model.Status;
import com.besttime.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CancelWebhookHandler implements WebhookHandler {
    private final PaymentRepository paymentRepository;

    @Override
    public String handleWebhook(String transactionId) {
        log.info("Transaction Id: {} is Cancelled.", transactionId);
        Optional<Payment> cancelledTransaction = paymentRepository.findByUniqueTransactionId(transactionId);
        cancelledTransaction.ifPresent(payment -> {
            log.info("Updating transaction status...");
            payment.setStatus(String.valueOf(Status.CANCELLED));
            paymentRepository.save(payment);
        });

        return "Payment Cancelled for: " + transactionId;
    }
}