package com.besttime.util;

import com.besttime.constant.AppConstant;

import java.time.Instant;
import java.util.UUID;

public class PaymentServiceUtil {

    public static String generateUniqueTransactionId() {
        return UUID.randomUUID().toString()
                .concat(AppConstant.HYPHEN)
                .concat(String.valueOf(Instant.now().getEpochSecond()));
    }
}
