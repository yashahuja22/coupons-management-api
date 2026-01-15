package com.codewithyash.Coupons.For.Ecommerce.models;

import lombok.Data;

import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
}
