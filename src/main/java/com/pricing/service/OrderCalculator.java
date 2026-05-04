package com.pricing.service;

import com.pricing.model.CustomerType;
import com.pricing.model.OrderItem;
import com.pricing.model.PriceResult;

import java.util.List;

public class OrderCalculator {

    private final DiscountService discountService;
    private final TaxService taxService;

    public OrderCalculator(DiscountService discountService, TaxService taxService) {
        this.discountService = discountService;
        this.taxService      = taxService;
    }

    public PriceResult calculate(List<OrderItem> items, CustomerType customerType, String discountCode) {
        double subtotal        = computeSubtotal(items);
        double discountRate    = discountService.getDiscountRate(customerType, discountCode);
        double discountAmount  = subtotal * discountRate;
        double afterDiscount   = subtotal - discountAmount;
        double tax             = taxService.calculateTax(afterDiscount);
        double finalPrice      = afterDiscount + tax;

        return new PriceResult(subtotal, discountAmount, tax, finalPrice);
    }

    private double computeSubtotal(List<OrderItem> items) {
        return items.stream()
                    .mapToDouble(OrderItem::getLineTotal)
                    .sum();
    }
}
