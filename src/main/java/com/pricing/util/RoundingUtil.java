package com.pricing.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundingUtil {

    private RoundingUtil() {}

    public static double round(double value) {
        return BigDecimal.valueOf(value)
                         .setScale(2, RoundingMode.HALF_UP)
                         .doubleValue();
    }
}
