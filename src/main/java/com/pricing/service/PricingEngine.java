package com.pricing.service;

// BAD DESIGN — starter code for refactoring lab
// Problems: god class, magic numbers, no separation of concerns, poor naming
public class PricingEngine {

    public double[] calc(double[] p, int[] q, String c, String code) {
        double sub = 0;
        for (int i = 0; i < p.length; i++) sub += p[i] * q[i];

        double disc = 0;
        if (c.equals("VIP")) disc += sub * 0.10;
        if (code != null && code.equals("SAVE10")) disc += sub * 0.10;
        if (code != null && code.equals("SAVE20")) disc += sub * 0.20;

        double afterDisc = sub - disc;
        double tax = afterDisc * 0.08;
        double fin = afterDisc + tax;

        return new double[]{sub, disc, tax, fin};
    }
}
