package com.flz.mydubbo.common.utils;

import java.util.UUID;

public final class RandomUtils {

    private RandomUtils() {

    }

    public static String uuid32() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
