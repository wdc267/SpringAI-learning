package com.wdc.springaideepseekdemo.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class CalculatorService {
    public record AddOperation(int a, int b) {

    }

    public record MulOperation(int m, int n) {

    }

    // 输入是一个 AddOperation（装着 a 和 b），输出是一个 Integer（计算结果）
    @Bean
    @Description("加法运算")
    public Function<AddOperation, Integer> addOperation() {
        return request -> {
            return request.a + request.b;
        };
    }

    @Bean
    @Description("乘法运算")
    public Function<MulOperation, Integer> mulOperation() {
        return request -> {
            return request.m * request.n;
        };
    }
}
