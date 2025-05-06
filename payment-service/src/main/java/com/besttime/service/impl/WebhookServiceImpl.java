package com.besttime.service.impl;

import com.besttime.config.AppConfig;
import com.besttime.handler.WebhookHandler;
import com.besttime.handler.impl.SuccessWebhookHandler;
import com.besttime.model.Status;
import com.besttime.repository.PaymentRepository;
import com.besttime.service.WebhookService;
import com.besttime.service.helper.StripeCheckoutSession;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebhookServiceImpl implements WebhookService {

    private final PaymentRepository paymentRepository;
    private final StripeCheckoutSession stripeCheckoutSession;
    private final AppConfig appConfig;

    @Override
    public String processWebhook(String transactionId, Status transactionStatus) {
        String response = null;
        WebhookHandler webhookHandler;
        if (transactionStatus.equals(Status.SUCCESS)) {
            webhookHandler = new SuccessWebhookHandler(paymentRepository, stripeCheckoutSession);
            response = webhookHandler.handleWebhook(transactionId);
        }
        if (transactionStatus.equals(Status.CANCELLED)) {
            webhookHandler = new SuccessWebhookHandler(paymentRepository, stripeCheckoutSession);
            response = webhookHandler.handleWebhook(transactionId);
        }

        return response;
    }

    @Override
    public boolean isValidRequest(String payload, String stripeSignature) {
        String webhookSecret = appConfig.getSecretKey(); // Replace with your Stripe webhook secret
        try {
            Webhook.constructEvent(payload, stripeSignature, webhookSecret);
        } catch (SignatureVerificationException e) {
            throw new RuntimeException(e);
        }
        return true; // Signature is valid
    }

}
