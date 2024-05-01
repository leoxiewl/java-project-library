package com.example.codeduplicationdemo01.controller;

import com.example.codeduplicationdemo01.entity.Cart;
import com.example.codeduplicationdemo01.service.InternalUserCart;
import com.example.codeduplicationdemo01.service.NormalUserCart;
import com.example.codeduplicationdemo01.service.VipUserCart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CartController {

    private static Map<Long,Integer> items = new HashMap<>();

    @GetMapping("/wrong")
    public Cart wrong(@RequestParam("userId") int userId) {
        // 初始化
        items.put(123456L,1);
        items.put(123457L,2);
        items.put(123458L,3);
        items.put(123459L,4);

        //根据用户ID获得用户类型
        String userCategory = "Internal";
        //普通用户处理逻辑
        if (userCategory.equals("Normal")) {
            NormalUserCart normalUserCart = new NormalUserCart();
            return normalUserCart.process(userId, items);
        }
        //VIP用户处理逻辑
        if (userCategory.equals("Vip")) {
            VipUserCart vipUserCart = new VipUserCart();
            return vipUserCart.process(userId, items);
        }
        //内部用户处理逻辑
        if (userCategory.equals("Internal")) {
            InternalUserCart internalUserCart = new InternalUserCart();
            return internalUserCart.process(userId, items);
        }

        return null;
    }
}
