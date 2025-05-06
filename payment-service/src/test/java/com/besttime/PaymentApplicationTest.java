package com.besttime;

import com.besttime.controller.webkook.StripeWebhookController;
import com.besttime.service.WebhookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PaymentApplicationTest {

    @MockBean
    private WebhookService webhookService;

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> PaymentApplication.main(new String[]{}));
    }
}
