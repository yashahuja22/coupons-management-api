package com.codewithyash.Coupons.For.Ecommerce.controllers.strategy;

import com.codewithyash.Coupons.For.Ecommerce.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductWiseCouponStrategy implements CouponStrategy {
    private final ObjectMapper mapper;

    private ProductWiseDetails getDetails(Coupon coupon) {
        return mapper.convertValue(
                coupon.getDetails(),
                ProductWiseDetails.class
        );
    }

    @Override
    public boolean isApplicable(Cart cart, Coupon coupon) {
        ProductWiseDetails details = getDetails(coupon);
        return cart.getItems().stream()
                .anyMatch(i -> i.getProductId().equals(details.getProductId()));
    }

    @Override
    public DiscountResult apply(Cart cart, Coupon coupon) {
        ProductWiseDetails details = getDetails(coupon);
        double totalDiscount = 0;

        for (CartItem item : cart.getItems()) {
            if (item.getProductId().equals(details.getProductId())) {
                double discount =
                        item.getPrice() * item.getQuantity()
                                * details.getDiscountPercentage() / 100;
                item.setTotalDiscount(discount);
                totalDiscount += discount;
            }
        }

        return new DiscountResult(totalDiscount, cart);
    }
}
