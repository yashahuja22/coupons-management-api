package com.codewithyash.Coupons.For.Ecommerce.models;

import lombok.Data;

import java.util.List;

@Data
public class BxGyDetails {
    private List<ProductQuantity> buyProducts;
    private List<ProductQuantity> getProducts;
    private int repetitionLimit;
}
