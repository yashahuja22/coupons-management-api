package com.codewithyash.Coupons.For.Ecommerce.controllers.strategy;

import com.codewithyash.Coupons.For.Ecommerce.models.Cart;
import com.codewithyash.Coupons.For.Ecommerce.models.CartWiseDetails;
import com.codewithyash.Coupons.For.Ecommerce.models.Coupon;
import com.codewithyash.Coupons.For.Ecommerce.models.DiscountResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartWiseCouponStrategy implements CouponStrategy {
    private final ObjectMapper mapper;

    private CartWiseDetails getDetails(Coupon coupon) {
        return mapper.convertValue(
                coupon.getDetails(),
                CartWiseDetails.class
        );
    }

    @Override
    public boolean isApplicable(Cart cart, Coupon coupon) {
        CartWiseDetails details = getDetails(coupon);
        double total = cart.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
        return total >= details.getThreshold();
    }

    @Override
    public DiscountResult apply(Cart cart, Coupon coupon) {
        CartWiseDetails details = getDetails(coupon);
        double total = cart.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        double discount = total * details.getDiscountPercentage() / 100;
        return new DiscountResult(discount, cart);
    }
}
