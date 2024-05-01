package com.example.codeduplicationdemo02.service;

import com.example.codeduplicationdemo02.entity.Cart;
import com.example.codeduplicationdemo02.entity.CartItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractCart {
    //处理购物车的大量重复逻辑在父类实现
    public Cart process(long userId, Map<Long, Integer> items) {

        Cart cart = new Cart();

        List<CartItem> itemList = new ArrayList<>();
        items.entrySet().stream().forEach(entry -> {
            CartItem item = new CartItem();
            item.setId(entry.getKey());
            item.setPrice(BigDecimal.valueOf(1));
            item.setQuantity(entry.getValue());
            itemList.add(item);
        });
        cart.setCartItems(itemList);
        //让子类处理每一个商品的优惠
        itemList.stream().forEach(item -> {
            processCouponPrice(userId, item);
            processDeliveryPrice(userId, item);
        });
        //计算商品总价
        cart.setTotalCartItemPrice(cart.getCartItems().stream().map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add));
        //计算总运费
        cart.setTotalDeliveryPrice(cart.getCartItems().stream().map(CartItem::getDeliveryPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        //计算总折扣
        cart.setTotalDiscount(cart.getCartItems().stream().map(CartItem::getCouponPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        //计算应付价格
        cart.setPayPrice(cart.getTotalCartItemPrice().add(cart.getTotalDeliveryPrice()).subtract(cart.getTotalDiscount()));
        return cart;
    }

    //处理商品优惠的逻辑留给子类实现
    protected abstract void processCouponPrice(long userId, CartItem item);
    //处理配送费的逻辑留给子类实现
    protected abstract void processDeliveryPrice(long userId, CartItem item);
}

