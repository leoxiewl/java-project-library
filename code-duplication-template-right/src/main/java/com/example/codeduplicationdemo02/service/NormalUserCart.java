package com.example.codeduplicationdemo02.service;

import com.example.codeduplicationdemo02.entity.CartItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service(value = "NormalUserCart")
public class NormalUserCart extends AbstractCart {
    @Override
    protected void processCouponPrice(long userId, CartItem item) {
        item.setCouponPrice(BigDecimal.ZERO);
    }

    @Override
    protected void processDeliveryPrice(long userId, CartItem item) {
        item.setDeliveryPrice(item.getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()))
                .multiply(new BigDecimal("0.1")));
    }
}
