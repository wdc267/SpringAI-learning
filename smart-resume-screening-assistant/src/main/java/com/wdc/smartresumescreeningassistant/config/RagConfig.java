package com.wdc.smartresumescreeningassistant.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RagConfig {
    @Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel)
                .build();
        //1 提取文本内容
        String filePath="张三简历.txt";
        TextReader textReader = new TextReader(filePath);
        textReader.getCustomMetadata().put("filePath",filePath); // 向 textReader 对象的"自定义元数据"映射（Map）中存入一个键值对，键为 "filePath"，值为 filePath 变量的内容
        List<Document> documents = textReader.get();
        //2 文本切分段落
        // minChunkSizeChars 每个文本块的最小字符长度限制。
        // minChunkLengthToEmbed 允许被向量化（Embedding）的最小文本块字符长度
        // keepSeparator 是否保留原始的分隔符
        TokenTextSplitter splitter =
                new TokenTextSplitter(1200,
                        350, 5,
                        100, true);
        splitter.apply(documents);
        //3 添加
        simpleVectorStore.add(documents);
        return simpleVectorStore;
    }
}
