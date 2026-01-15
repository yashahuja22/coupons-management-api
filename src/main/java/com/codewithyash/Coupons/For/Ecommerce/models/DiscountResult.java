package com.codewithyash.Coupons.For.Ecommerce.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiscountResult {
    private double discountAmount;
    private Cart updatedCart;
}
