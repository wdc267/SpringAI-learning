package com.wdc.springaiother.controller;

import com.alibaba.cloud.ai.dashscope.api.DashScopeImageApi;
import com.alibaba.cloud.ai.dashscope.audio.tts.DashScopeAudioSpeechModel;
import com.alibaba.cloud.ai.dashscope.audio.tts.DashScopeAudioSpeechOptions;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.List;

@RestController
public class OtherController {
    @Autowired
    private DashScopeImageModel imageModel;

    @Autowired
    private DashScopeAudioSpeechModel speechModel;

    private static final String TEXT = "床前明月光， 疑是地上霜。 举头望明月， 低头思故乡。";
    private static final String FILE_PATH = "src/main/resources/tts";

    @GetMapping("/image")
    public void getImageabli(@RequestParam(value = "msg", defaultValue = "生成一只小猫")
                                 String msg, HttpServletResponse res){
        ImageResponse response = imageModel.call(
                new ImagePrompt(
                        msg,
                        DashScopeImageOptions.builder()
                                .withModel("wanx2.1-t2i-turbo")
                                .withN(1)//要生成的图像数。必须介于 1 和 10 之间。
                                .withHeight(1024)//生成的图像的高宽度。
                                .withWidth(1024).build())
                );
        String imageUrl = response.getResult().getOutput().getUrl();
        try{
            //使用输出流在浏览器输出
            URL url = URI.create(imageUrl).toURL();
            InputStream in = url.openStream();
            res.setHeader("Content-Type", MediaType.IMAGE_PNG_VALUE);
            res.getOutputStream().write(in.readAllBytes());
            res.getOutputStream().flush();
        }catch (Exception e){

        }
    }


    @GetMapping("/tts")
    public void tts() throws IOException {
        DashScopeAudioSpeechOptions options = DashScopeAudioSpeechOptions.builder()
                .model("cosyvoice-v3-flash")
                .voice("longanyang")
                .format("mp3")
                .sampleRate(22050)          // 建议加上，保证输出格式一致
                .textType("PlainText")      // 明确输入是普通文本
                .build();

        TextToSpeechPrompt prompt = new TextToSpeechPrompt(TEXT, options);

        // 关键：用 stream() 获取音频分片流，收集所有分片
        Flux<byte[]> audioFlux = speechModel.stream(prompt)
                .map(response -> response.getResult().getOutput());
        List<byte[]> chunks = audioFlux.collectList().block();  // 阻塞等待所有分片返回

        if (chunks == null || chunks.isEmpty()) {
            throw new IOException("语音合成未返回任何音频数据");
        }

        // 把所有分片按顺序拼接成一个完整的字节数组
        int totalLength = 0;
        for (byte[] chunk : chunks) {
            totalLength += chunk.length;
        }
        byte[] fullAudio = new byte[totalLength];
        int offset = 0;
        for (byte[] chunk : chunks) {
            System.arraycopy(chunk, 0, fullAudio, offset, chunk.length);
            offset += chunk.length;
        }

        // 确保目录存在后写入文件
        File dir = new File(FILE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, "output.mp3");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(fullAudio);
        }
    }

}
