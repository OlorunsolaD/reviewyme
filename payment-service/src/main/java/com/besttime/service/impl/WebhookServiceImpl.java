package com.besttime.service.impl;

import com.besttime.handler.WebhookHandler;
import com.besttime.handler.impl.SuccessWebhookHandler;
import com.besttime.model.Status;
import com.besttime.repository.PaymentRepository;
import com.besttime.service.WebhookService;
import com.besttime.service.helper.StripeCheckoutSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebhookServiceImpl implements WebhookService {

    private final PaymentRepository paymentRepository;
    private final StripeCheckoutSession stripeCheckoutSession;

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
}
