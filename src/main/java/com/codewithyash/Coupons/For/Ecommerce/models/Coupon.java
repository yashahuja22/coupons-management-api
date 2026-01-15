package com.codewithyash.Coupons.For.Ecommerce.models;

import lombok.Data;

@Data
public class Coupon {
    private Long id;
    private CouponType type;
    private Object details;
    private boolean active = true;
}
