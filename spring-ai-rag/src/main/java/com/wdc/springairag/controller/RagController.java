package com.wdc.springairag.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagController {
    @Autowired
    private ChatClient dashScopeChatClient;

    @Autowired
    private VectorStore vectorStore;

    @GetMapping(value = "/chat", produces = "text/plain; charset=UTF-8")
    public String generation(String userInput) {
        // 发起聊天请求并处理响应
        return dashScopeChatClient.prompt()
                .user(userInput)
                .advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                .call()
                .content();
    }
}
