package com.besttime.handler.impl;

import com.besttime.entity.Payment;
import com.besttime.handler.WebhookHandler;
import com.besttime.model.Status;
import com.besttime.repository.PaymentRepository;
import com.besttime.service.helper.StripeCheckoutSession;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Slf4j
@RequiredArgsConstructor
public class SuccessWebhookHandler implements WebhookHandler {

    private final PaymentRepository paymentRepository;
    private final StripeCheckoutSession stripeCheckoutSession;

    @Override
    public String handleWebhook(String transactionId) {
        log.info("Transaction Id: {} is Success", transactionId);
        AtomicReference<Status> validatedTransactionStatus = new AtomicReference<>();
        Optional<Payment> successTransaction = paymentRepository.findByUniqueTransactionId(transactionId);
        successTransaction.ifPresent(payment -> {
            log.info("Updating transaction status...");
            try {
                if (stripeCheckoutSession.isPaid(payment.getReferenceId()))
                    validatedTransactionStatus.set(Status.SUCCESS);
                else
                    validatedTransactionStatus.set(Status.CANCELLED);
            } catch (StripeException e) {
                throw new RuntimeException(e);
            }
            payment.setStatus(String.valueOf(validatedTransactionStatus.get()));
            paymentRepository.save(payment);
        });

        return "Payment for: " + transactionId + " was " + validatedTransactionStatus;
    }
}
