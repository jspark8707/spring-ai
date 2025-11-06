package com.d2dev.springai.controller;

import com.d2dev.springai.AnswerResponse;
import com.d2dev.springai.ChatRequest;
import com.d2dev.springai.service.ChatService;
import com.d2dev.springai.service.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SlackService slackService;  // ✅ SlackService 주입

    @Value("${spring.profiles.active:local}")
    private String activeProfile;

    /** ✅ 프론트엔드 페이지 렌더링 (Thymeleaf) */
    @GetMapping({"/", "/chat"})
    public String chatPage(Model model) {
        model.addAttribute("activeProfile", activeProfile);
        return "chat";  // templates/chat.html
    }

    /** ✅ 채팅 요청 API */
    @PostMapping("/api/chat")
    @ResponseBody
    public AnswerResponse chat(@RequestBody ChatRequest request) {
        // 1️⃣ 사용자 질문 처리
        AnswerResponse response = chatService.chat(request);

        // 2️⃣ 운영(prod) 환경에서만 Slack 전송
        if ("prod".equalsIgnoreCase(activeProfile)) {
            String userMsg = request.getMessage();
            String aiReply = response.getAnswer();
            String slackText = String.format(
                    "💬 *NRF Spring AI Chat 메시지 로그*\n" +
                            "> 👤 사용자: %s\n" +
                            "> 🤖 답변: %s",
                    userMsg, aiReply
            );

            slackService.sendMessage(slackText);
        } else {
            System.out.println("🚫 Slack 비활성화 (현재 프로필: " + activeProfile + ")");
        }

        return response;
    }

    /** ✅ 헬스체크용 API */
    @GetMapping("/api/chat/ping")
    @ResponseBody
    public Map<String, String> ping() {
        return Map.of("status", "ok", "service", "spring-ai");
    }
}
