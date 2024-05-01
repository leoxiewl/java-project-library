package com.example.codeduplicationannotationreflectwrong.controller;

import java.io.IOException;
import java.math.BigDecimal;

import static com.example.codeduplicationannotationreflectwrong.service.BankAPIService.remoteCall;

public class Controller {
    //创建用户方法
    public static String createUser(String name, String identity, String mobile, int age) throws IOException {
        CreateUserAPI createUserAPI = new CreateUserAPI();
        createUserAPI.setName(name);
        createUserAPI.setIdentity(identity);
        createUserAPI.setAge(age);
        createUserAPI.setMobile(mobile);
        return remoteCall(createUserAPI);
    }
    //支付方法
    public static String pay(long userId, BigDecimal amount) throws IOException {
        PayAPI payAPI = new PayAPI();
        payAPI.setUserId(userId);
        payAPI.setAmount(amount);
        return remoteCall(payAPI);
    }

}
