package com.example.codeduplicationannotationreflectwrong;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BankServiceWrong {

    //创建用户方法
    public static String createUser(String name, String identity, String mobile, int age) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        //字符串靠左，多余的地方填充_
        stringBuilder.append(String.format("%-10s", name).replace(' ', '_'));
        //字符串靠左，多余的地方填充_
        stringBuilder.append(String.format("%-18s", identity).replace(' ', '_'));
        //数字靠右，多余的地方用0填充
        stringBuilder.append(String.format("%05d", age));
        //字符串靠左，多余的地方用_填充
        stringBuilder.append(String.format("%-11s", mobile).replace(' ', '_'));
        //最后加上MD5作为签名
        stringBuilder.append(DigestUtils.md2Hex(stringBuilder.toString()));
        return Request.Post("http://localhost:45678/reflection/bank/createUser")
                .bodyString(stringBuilder.toString(), ContentType.APPLICATION_JSON)
                .execute().returnContent().asString();
    }

    //支付方法
    public static String pay(long userId, BigDecimal amount) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        //数字靠右，多余的地方用0填充
        stringBuilder.append(String.format("%020d", userId));
        //金额向下舍入2位到分，以分为单位，作为数字靠右，多余的地方用0填充
        stringBuilder.append(String.format("%010d", amount.setScale(2, RoundingMode.DOWN).multiply(new BigDecimal("100")).longValue()));
        //最后加上MD5作为签名
        stringBuilder.append(DigestUtils.md2Hex(stringBuilder.toString()));
        return Request.Post("http://localhost:45678/reflection/bank/pay")
                .bodyString(stringBuilder.toString(), ContentType.APPLICATION_JSON)
                .execute().returnContent().asString();
    }
}

