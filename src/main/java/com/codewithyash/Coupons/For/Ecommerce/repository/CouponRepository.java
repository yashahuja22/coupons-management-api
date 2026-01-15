package com.codewithyash.Coupons.For.Ecommerce.repository;

import com.codewithyash.Coupons.For.Ecommerce.models.Coupon;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CouponRepository {
    private final Map<Long, Coupon> store = new HashMap<>();
    private long idCounter = 1;

    public Coupon save(Coupon coupon) {
        coupon.setId(idCounter++);
        store.put(coupon.getId(), coupon);
        return coupon;
    }

    public Optional<Coupon> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Coupon> findAll() {
        return new ArrayList<>(store.values());
    }

    public void delete(Long id) {
        store.remove(id);
    }
}
