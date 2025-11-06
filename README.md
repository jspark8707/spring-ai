#  NRF Spring AI Chat

Spring AI를 활용한 다중 AI 모델 채팅 애플리케이션

## 📌 프로젝트 개요

여러 AI 모델(OpenAI GPT, Ollama Llama)을 하나의 웹 인터페이스에서 사용할 수 있는 채팅 서비스입니다.

> 본 애플리케이션은 **Google Cloud VM (Debian 기반)** 환경에서 운영되고 있으며,  
> Spring Boot 백엔드와 Ollama 로컬 LLM 서버를 함께 구동하여 서비스하고 있습니다.

## 🛠️ 기술 스택

- **Backend**: Spring Boot 3.x, Spring AI
- **Frontend**: HTML, CSS, Vanilla JavaScript
- **AI Models**: OpenAI GPT-4o Mini, Ollama Llama 3, Qwen2.5:1.5b
- **Build Tool**: Maven
- **Infra**: Google Cloud VM (Debian 기반, n1-standard-1)

## ✨ 주요 기능

- 🔄 실시간 AI 모델 전환 (OpenAI ↔ Ollama)
- 💬 실시간 채팅 인터페이스
- 📝 대화 기록 로깅 (Logback)
- 🎨 반응형 UI 디자인

## ☁️ 서비스 구성도

```mermaid
graph TD
  User[🧑‍💻 Web Client] -->|HTTP| SpringAI[Spring Boot Server]
  SpringAI -->|REST| Ollama[Ollama LLM Server]
  SpringAI -->|API| OpenAI[OpenAI GPT API]
  SpringAI -->|Deployed on| GCP[Google Cloud VM]
