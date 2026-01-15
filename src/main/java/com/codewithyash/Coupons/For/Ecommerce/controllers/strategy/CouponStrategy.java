package com.codewithyash.Coupons.For.Ecommerce.controllers.strategy;

import com.codewithyash.Coupons.For.Ecommerce.models.Cart;
import com.codewithyash.Coupons.For.Ecommerce.models.Coupon;
import com.codewithyash.Coupons.For.Ecommerce.models.DiscountResult;

public interface CouponStrategy {
    boolean isApplicable(Cart cart, Coupon coupon);
    DiscountResult apply(Cart cart, Coupon coupon);
}
