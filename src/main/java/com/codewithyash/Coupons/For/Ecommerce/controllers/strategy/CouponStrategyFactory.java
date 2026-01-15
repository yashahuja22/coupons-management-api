package com.codewithyash.Coupons.For.Ecommerce.controllers.strategy;

import com.codewithyash.Coupons.For.Ecommerce.models.CouponType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponStrategyFactory {
    private final CartWiseCouponStrategy cartWise;
    private final ProductWiseCouponStrategy productWise;
    private final BxGyCouponStrategy bxGy;

    public CouponStrategy getStrategy(CouponType type) {
        return switch (type) {
            case BXGY -> bxGy;
            case PRODUCT_WISE -> productWise;
            case CART_WISE -> cartWise;
        };
    }
}
