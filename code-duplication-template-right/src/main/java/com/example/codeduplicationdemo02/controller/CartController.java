package com.example.codeduplicationdemo02.controller;

import com.example.codeduplicationdemo02.entity.Cart;
import com.example.codeduplicationdemo02.service.AbstractCart;
import com.example.codeduplicationdemo02.util.ApplicationContextHolder;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CartController {
    private static Map<Long,Integer> items = new HashMap<>();

    @GetMapping("right")
    public Cart right(@RequestParam("userId") int userId) {
        String userCategory = "Vip";

        // 初始化
        items.put(123456L,1);
        items.put(123457L,2);
        items.put(123458L,3);
        items.put(123459L,4);

        AbstractCart cart = (AbstractCart) ApplicationContextHolder.getContext().getBean(userCategory + "UserCart");
        return cart.process(userId, items);
    }
}
