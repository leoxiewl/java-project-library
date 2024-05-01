package com.example.codeduplicationdemo01.service;

import com.example.codeduplicationdemo01.entity.Cart;
import com.example.codeduplicationdemo01.entity.CartItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InternalUserCart {
    public Cart process(long userId, Map<Long, Integer> items) {
        Cart cart = new Cart();

        //把Map的购物车转换为CartItem列表
        List<CartItem> itemList = new ArrayList<>();
        items.entrySet().stream().forEach(entry -> {
            CartItem item = new CartItem();
            item.setId(entry.getKey());
            item.setPrice(BigDecimal.valueOf(1));
            item.setQuantity(entry.getValue());
            itemList.add(item);
        });
        cart.setCartItems(itemList);

        //处理运费和商品优惠
        itemList.stream().forEach(item -> {
            //免运费
            item.setDeliveryPrice(BigDecimal.ZERO);
            //无优惠
            item.setCouponPrice(BigDecimal.ZERO);
        });

        //计算商品总价
        cart.setTotalCartItemPrice(cart.getCartItems().stream().map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add));
        //计算运费总价
        cart.setTotalDeliveryPrice(cart.getCartItems().stream().map(CartItem::getDeliveryPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        //计算总优惠
        cart.setTotalDiscount(cart.getCartItems().stream().map(CartItem::getCouponPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        //应付总价=商品总价+运费总价-总优惠
        cart.setPayPrice(cart.getTotalCartItemPrice().add(cart.getTotalDeliveryPrice()).subtract(cart.getTotalDiscount()));
        return cart;
    }
}
