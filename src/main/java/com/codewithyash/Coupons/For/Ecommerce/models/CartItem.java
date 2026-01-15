package com.codewithyash.Coupons.For.Ecommerce.models;

import lombok.Data;

@Data
public class CartItem {
    private Long productId;
    private int quantity;
    private double price;
    private double totalDiscount;
}
