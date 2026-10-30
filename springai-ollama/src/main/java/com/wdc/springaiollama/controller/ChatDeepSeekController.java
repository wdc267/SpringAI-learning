package com.wdc.springaiollama.controller;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatDeepSeekController {

    @Autowired
    private OllamaChatModel ollamaChatModel;

    @GetMapping("/ai/test")
    public String generate(@RequestParam(value = "message", defaultValue = "hello")
                           String message) {
        String response = this.ollamaChatModel.call(message);
        System.out.println("response : "+response);
        return response;
    }
}
