package com.wdc.smartresumescreeningassistant.utils;

import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@Description("某某是否有资格面试")
public class RecruitServiceFunction implements Function<RecruitServiceFunction.Request, RecruitServiceFunction.Response> {

    @Override
    public Response apply(Request request) {
        String position="未知";
        if(request.name.contains("张三")){
            position="算法工程师";
        }
        return new Response(position);
    }

    public record Request(String name){ }
    public record Response(String position){ }

}
