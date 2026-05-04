package com.pricing.service;

import com.pricing.model.CustomerType;

public class DiscountService {

    // Returns a discount rate between 0.0 and 1.0
    public double getDiscountRate(CustomerType customerType, String discountCode) {
        double rate = getCustomerDiscount(customerType);
        rate += getCodeDiscount(discountCode);
        return Math.min(rate, 0.50); // cap at 50%
    }

    private double getCustomerDiscount(CustomerType customerType) {
        return customerType == CustomerType.VIP ? 0.10 : 0.0;
    }

    private double getCodeDiscount(String code) {
        if (code == null) return 0.0;
        return switch (code.toUpperCase()) {
            case "SAVE10" -> 0.10;
            case "SAVE20" -> 0.20;
            case "SAVE30" -> 0.30;
            default       -> 0.0;
        };
    }
}
