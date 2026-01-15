package com.codewithyash.Coupons.For.Ecommerce.controllers.strategy;

import com.codewithyash.Coupons.For.Ecommerce.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BxGyCouponStrategy implements CouponStrategy {
    private final ObjectMapper mapper;

    private BxGyDetails getDetails(Coupon coupon) {
        return mapper.convertValue(
                coupon.getDetails(),
                BxGyDetails.class
        );
    }

    @Override
    public boolean isApplicable(Cart cart, Coupon coupon) {
        BxGyDetails details = getDetails(coupon);

        for (ProductQuantity pq : details.getBuyProducts()) {
            int cartQty = cart.getItems().stream()
                    .filter(i -> i.getProductId().equals(pq.getProductId()))
                    .mapToInt(CartItem::getQuantity)
                    .sum();

            if (cartQty < pq.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public DiscountResult apply(Cart cart, Coupon coupon) {
        BxGyDetails details = getDetails(coupon);

        double totalDiscount = 0;
        int applications = details.getRepetitionLimit();

        for (ProductQuantity free : details.getGetProducts()) {
            for (CartItem item : cart.getItems()) {
                if (item.getProductId().equals(free.getProductId())
                        && applications > 0) {

                    int freeQty = Math.min(free.getQuantity(), applications);
                    double discount = freeQty * item.getPrice();

                    item.setQuantity(item.getQuantity() + freeQty);
                    item.setTotalDiscount(discount);

                    totalDiscount += discount;
                    applications--;
                }
            }
        }

        return new DiscountResult(totalDiscount, cart);
    }
}
