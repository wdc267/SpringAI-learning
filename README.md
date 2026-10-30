# Spring AI Learning

Spring AI 学习与示例项目合集，包含多个相互独立的 Spring Boot + Spring AI 模块，覆盖聊天对话、流式输出、Prompt 模板、工具调用、RAG、文生图、语音合成以及本地 Ollama 等常见场景。

## 模块概览

| 模块 | 说明 | 主要接口 |
| --- | --- | --- |
| `smart-resume-screening-assistant` | 智能简历筛选助手，结合 RAG 与 Function Calling 进行候选人-职位匹配 | `GET /ai/agent?query=...` |
| `spring-ai-alibaba-demo` | Spring AI Alibaba（DashScope）基础对话示例 | `GET /simple/chat?query=...` |
| `spring-ai-deepseek-demo` | DeepSeek（OpenAI 兼容协议）示例：普通/流式对话、Prompt 模板、工具调用 | `/chat`、`/chat/stream`、`/chatModel01`、`/chatModel02`、`/prompt`、`/caculate` |
| `spring-ai-other` | 多模态示例：文生图与语音合成（TTS） | `GET /image?msg=...`、`GET /tts` |
| `spring-ai-rag` | RAG 检索增强生成示例 | `GET /chat?userInput=...` |
| `springai-ollama` | 本地 Ollama 模型对话示例（默认 `deepseek-r1:1.5b`） | `GET /ai/test?message=...` |

> 各模块均为独立的 Maven 工程，没有父 `pom.xml`，需要分别进入模块目录构建和启动。

## 技术栈

- Java 21
- Maven（各模块自带 Maven Wrapper）
- Spring Boot 3.3.8 / 3.5.x
- Spring AI 1.0.0-M6
- Spring AI Alibaba 1.1.2.2
- 模型服务：阿里云 DashScope（通义千问）、DeepSeek、Ollama

## 配置 API Key

出于安全考虑，仓库中不包含任何真实密钥，相关配置已改为环境变量占位符：

```properties
spring.ai.dashscope.api-key=${DASHSCOPE_API_KEY}
spring.ai.openai.api-key=${DEEPSEEK_API_KEY}
```

运行前请按需设置环境变量：

```powershell
# Windows PowerShell
$env:DASHSCOPE_API_KEY="your-dashscope-key"
$env:DEEPSEEK_API_KEY="your-deepseek-key"
```

```bash
# Linux / macOS
export DASHSCOPE_API_KEY="your-dashscope-key"
export DEEPSEEK_API_KEY="your-deepseek-key"
```

`springai-ollama` 模块使用本地 Ollama（默认 `http://localhost:11434`），无需 API Key。

## 运行

以 `spring-ai-rag` 为例：

```bash
cd spring-ai-rag
./mvnw spring-boot:run          # Linux / macOS
mvnw.cmd spring-boot:run        # Windows
```

启动后访问对应接口，例如：

```
http://localhost:8899/chat?userInput=什么是Spring AI
```

大部分模块的默认端口为 `8899`，具体以各模块 `src/main/resources/application.properties` 为准。

## 目录结构

```
SpringAI/
├── smart-resume-screening-assistant/
├── spring-ai-alibaba-demo/
├── spring-ai-deepseek-demo/
├── spring-ai-other/
├── spring-ai-rag/
├── springai-ollama/
└── README.md
```

## 说明

- 请勿将真实 API Key、日志文件、构建产物提交到仓库。
- 各模块可独立运行，按需学习对应示例即可。
