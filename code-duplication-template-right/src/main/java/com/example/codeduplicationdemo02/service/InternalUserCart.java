package com.example.codeduplicationdemo02.service;

import com.example.codeduplicationdemo02.entity.CartItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service(value = "InternalUserCart")
public class InternalUserCart extends AbstractCart {
    @Override
    protected void processCouponPrice(long userId, CartItem item) {
        item.setCouponPrice(BigDecimal.ZERO);
    }

    @Override
    protected void processDeliveryPrice(long userId, CartItem item) {
        item.setDeliveryPrice(BigDecimal.ZERO);
    }
}

