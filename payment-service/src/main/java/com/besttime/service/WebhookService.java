package com.besttime.service;

import com.besttime.model.Status;

public interface WebhookService {
    String processWebhook(String transactionId, Status transactionStatus);
}
