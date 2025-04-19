package com.besttime.util;

import com.besttime.constant.AppConstant;
import org.apache.logging.log4j.util.Strings;

import java.time.Instant;
import java.util.UUID;

import static com.besttime.constant.AppConstant.HYPHEN;

public class PaymentServiceUtil {

    public static String generateUniqueTransactionId() {
        return UUID.randomUUID().toString()
                .concat(HYPHEN)
                .concat(String.valueOf(Instant.now().getEpochSecond()));
    }

    public static String generateInternalSessionId() {
        return UUID.randomUUID().toString()
                .concat(HYPHEN)
                .concat(String.valueOf(Instant.now().getEpochSecond()))
                .replace(HYPHEN, Strings.EMPTY);
    }
}
