package com.pricing.service;

public class TaxService {

    private static final double TAX_RATE = 0.08; // 8%

    public double calculateTax(double amountAfterDiscount) {
        return amountAfterDiscount * TAX_RATE;
    }

    public double getTaxRate() {
        return TAX_RATE;
    }
}
