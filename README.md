#  NRF Spring AI Chat

Spring AI를 활용한 다중 AI 모델 채팅 애플리케이션

## 📌 프로젝트 개요

여러 AI 모델(OpenAI GPT, Ollama Llama)을 하나의 웹 인터페이스에서 사용할 수 있는 채팅 서비스입니다.

## 🛠️ 기술 스택

- **Backend**: Spring Boot 3.x, Spring AI
- **Frontend**: HTML, CSS, Vanilla JavaScript
- **AI Models**: OpenAI GPT-4o Mini, Ollama Llama 3, Qwen2.5:1.5b
- **Build Tool**: Maven

## ✨ 주요 기능

- 🔄 실시간 AI 모델 전환 (OpenAI ↔ Ollama)
- 💬 실시간 채팅 인터페이스
- 📝 대화 기록 로깅 (Logback)
- 🎨 반응형 UI 디자인

## 🚀 실행 방법

1. **API 키 설정**
   ```properties
   # src/main/resources/application_bak.properties
   spring.ai.openai.api-key=your-api-key-here
   ```

2. **애플리케이션 실행**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **브라우저 접속**
   ```
   http://localhost:8080
   ```

## 📚 라이선스   
- MIT License
