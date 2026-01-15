package com.codewithyash.Coupons.For.Ecommerce.service;

import com.codewithyash.Coupons.For.Ecommerce.controllers.strategy.CouponStrategy;
import com.codewithyash.Coupons.For.Ecommerce.controllers.strategy.CouponStrategyFactory;
import com.codewithyash.Coupons.For.Ecommerce.exception.CouponNotFoundException;
import com.codewithyash.Coupons.For.Ecommerce.models.ApplicableCouponResponse;
import com.codewithyash.Coupons.For.Ecommerce.models.Cart;
import com.codewithyash.Coupons.For.Ecommerce.models.Coupon;
import com.codewithyash.Coupons.For.Ecommerce.models.DiscountResult;
import com.codewithyash.Coupons.For.Ecommerce.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponStrategyFactory strategyFactory;

//    CRUD

    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public List<Coupon> getAll() {
        return couponRepository.findAll();
    }

    public Coupon getById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException(id));
    }

    public void delete(Long id) {
        couponRepository.delete(id);
    }

    public List<ApplicableCouponResponse> getApplicableCoupons(Cart cart) {
        return couponRepository.findAll().stream()
                .filter(Coupon::isActive)
                .map(coupon -> {
                    CouponStrategy strategy =
                            strategyFactory.getStrategy(coupon.getType());
                    if (strategy.isApplicable(cart, coupon)) {
                        DiscountResult result = strategy.apply(cart, coupon);
                        return new ApplicableCouponResponse(
                                coupon.getId(),
                                coupon.getType().name(),
                                result.getDiscountAmount()
                        );
                    }
                    return null;
                })
                .filter(c -> c != null)
                .collect(Collectors.toList());
    }


    public DiscountResult applyCoupon(Long couponId, Cart cart) {
        Coupon coupon = getById(couponId);
        CouponStrategy strategy = strategyFactory.getStrategy(coupon.getType());
        if (!strategy.isApplicable(cart, coupon)) {
            throw new IllegalStateException("Coupon conditions not met");
        }

        return strategy.apply(cart, coupon);
    }
}
