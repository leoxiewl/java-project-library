package com.example.codeduplicationannotationreflectwrong.service;

import com.example.codeduplicationannotationreflectwrong.annotation.BankAPI;
import com.example.codeduplicationannotationreflectwrong.annotation.BankAPIField;
import com.example.codeduplicationannotationreflectwrong.controller.AbstractAPI;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class BankAPIService {
    // 所有处理参数排序、填充、加签、请求调用的核心逻辑，都汇聚在了 remoteCall 方法中
    public static String remoteCall(AbstractAPI api) throws IOException {
        //从BankAPI注解获取请求地址
        BankAPI bankAPI = api.getClass().getAnnotation(BankAPI.class);
        bankAPI.url();
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(api.getClass().getDeclaredFields()) //获得所有字段
                .filter(field -> field.isAnnotationPresent(BankAPIField.class)) //查找标记了注解的字段
                .sorted(Comparator.comparingInt(a -> a.getAnnotation(BankAPIField.class).order())) //根据注解中的order对字段排序
                .peek(field -> field.setAccessible(true)) //设置可以访问私有字段
                .forEach(field -> {
                    //获得注解
                    BankAPIField bankAPIField = field.getAnnotation(BankAPIField.class);
                    Object value = "";
                    try {
                        //反射获取字段值
                        value = field.get(api);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    //根据字段类型以正确的填充方式格式化字符串
                    switch (bankAPIField.type()) {
                        case "S": {
                            stringBuilder.append(String.format("%-" + bankAPIField.length() + "s", value.toString()).replace(' ', '_'));
                            break;
                        }
                        case "N": {
                            stringBuilder.append(String.format("%" + bankAPIField.length() + "s", value.toString()).replace(' ', '0'));
                            break;
                        }
                        case "M": {
                            if (!(value instanceof BigDecimal))
                                throw new RuntimeException(String.format("{} 的 {} 必须是BigDecimal", api, field));
                            stringBuilder.append(String.format("%0" + bankAPIField.length() + "d", ((BigDecimal) value).setScale(2, RoundingMode.DOWN).multiply(new BigDecimal("100")).longValue()));
                            break;
                        }
                        default:
                            break;
                    }
                });
        //签名逻辑
        stringBuilder.append(DigestUtils.md2Hex(stringBuilder.toString()));
        String param = stringBuilder.toString();
        long begin = System.currentTimeMillis();
        //发请求
        String result = Request.Post("http://localhost:45678/reflection" + bankAPI.url())
                .bodyString(param, ContentType.APPLICATION_JSON)
                .execute().returnContent().asString();
        log.info("调用银行API {} url:{} 参数:{} 耗时:{}ms", bankAPI.desc(), bankAPI.url(), param, System.currentTimeMillis() - begin);
        return result;
    }

}
