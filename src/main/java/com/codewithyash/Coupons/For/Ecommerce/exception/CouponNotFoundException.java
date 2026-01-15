package com.codewithyash.Coupons.For.Ecommerce.exception;

public class CouponNotFoundException extends RuntimeException {
    public CouponNotFoundException(Long id) {
        super("Coupon not found with id: " + id);
    }
}
