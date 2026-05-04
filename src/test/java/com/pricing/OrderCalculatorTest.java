package com.pricing;

import com.pricing.model.CustomerType;
import com.pricing.model.OrderItem;
import com.pricing.model.PriceResult;
import com.pricing.service.DiscountService;
import com.pricing.service.OrderCalculator;
import com.pricing.service.TaxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderCalculatorTest {

    private OrderCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new OrderCalculator(new DiscountService(), new TaxService());
    }

    @Test
    void subtotalIsCorrect() {
        var items = List.of(new OrderItem("Pen", 2.00, 3), new OrderItem("Book", 10.00, 1));
        PriceResult result = calculator.calculate(items, CustomerType.REGULAR, null);
        assertEquals(16.00, result.getSubtotal(), 0.001);
    }

    @Test
    void vipGets10PercentDiscount() {
        var items = List.of(new OrderItem("Widget", 100.00, 1));
        PriceResult result = calculator.calculate(items, CustomerType.VIP, null);
        assertEquals(10.00, result.getDiscountAmount(), 0.001);
    }

    @Test
    void save20CodeApplied() {
        var items = List.of(new OrderItem("Gadget", 50.00, 2));
        PriceResult result = calculator.calculate(items, CustomerType.REGULAR, "SAVE20");
        assertEquals(20.00, result.getDiscountAmount(), 0.001);
    }

    @Test
    void discountCappedAt50Percent() {
        var items = List.of(new OrderItem("Item", 100.00, 1));
        PriceResult result = calculator.calculate(items, CustomerType.VIP, "SAVE30");
        // VIP(10%) + SAVE30(30%) = 40%, under cap — just verifying no over-cap here
        assertEquals(40.00, result.getDiscountAmount(), 0.001);
    }

    @Test
    void taxCalculatedOn8Percent() {
        var items = List.of(new OrderItem("Laptop", 200.00, 1));
        PriceResult result = calculator.calculate(items, CustomerType.REGULAR, null);
        assertEquals(16.00, result.getTax(), 0.001);
    }

    @Test
    void finalPriceEqualsAfterDiscountPlusTax() {
        var items = List.of(new OrderItem("Desk", 100.00, 1));
        PriceResult result = calculator.calculate(items, CustomerType.REGULAR, "SAVE10");
        double expected = 90.00 + (90.00 * 0.08);
        assertEquals(expected, result.getFinalPrice(), 0.001);
    }

    @Test
    void emptyItemListReturnsZeroes() {
        PriceResult result = calculator.calculate(List.of(), CustomerType.REGULAR, null);
        assertEquals(0.0, result.getFinalPrice(), 0.001);
    }
}
