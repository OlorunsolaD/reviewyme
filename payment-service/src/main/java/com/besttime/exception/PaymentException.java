package com.besttime.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentException extends RuntimeException {
    private final String code;
    private final String reason;

    public PaymentException(String code, String message, String reason) {
        super(message);
        this.code = code;
        this.reason = reason;
    }

}
