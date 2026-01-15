package com.codewithyash.Coupons.For.Ecommerce.controllers;


import com.codewithyash.Coupons.For.Ecommerce.models.ApplicableCouponResponse;
import com.codewithyash.Coupons.For.Ecommerce.models.Cart;
import com.codewithyash.Coupons.For.Ecommerce.models.Coupon;
import com.codewithyash.Coupons.For.Ecommerce.models.DiscountResult;
import com.codewithyash.Coupons.For.Ecommerce.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponsController {
    private final CouponService service;

    @PostMapping
    public Coupon create(@RequestBody Coupon coupon) {
        return service.create(coupon);
    }

    @GetMapping
    public List<Coupon> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Coupon getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/applicable")
    public List<ApplicableCouponResponse> applicableCoupons(
            @RequestBody Cart cart) {
        return service.getApplicableCoupons(cart);
    }

    @PostMapping("/apply/{id}")
    public DiscountResult applyCoupon(
            @PathVariable Long id,
            @RequestBody Cart cart) {
        return service.applyCoupon(id, cart);
    }
}
