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

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public AnswerResponse chat(@RequestBody ChatRequest request, HttpServletRequest httpReq) {
        // 1️⃣ 사용자 질문 처리
        AnswerResponse response = chatService.chat(request);

        // 2️⃣ 운영(prod) 환경에서만 Slack 전송
        if ("prod".equalsIgnoreCase(activeProfile)) {

            // 🌐 사용자 정보 추출
            String clientIp = getClientIp(httpReq);
            String userAgent = httpReq.getHeader("User-Agent");
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String userMsg = request.getMessage();
            String aiReply = response.getAnswer();

            // 🧩 Slack 메시지 포맷 (가독성 향상)
            String slackText = String.format(
                    "*💬 NRF Spring AI Chat 로그*\n" +
                            "```" +
                            "🕓 %s\n" +
                            "🌐 IP: %s\n" +
                            "💻 UA: %s\n" +
                            "👤 질문: %s\n" +
                            "🤖 답변: %s" +
                            "```",
                    timestamp, clientIp, userAgent, userMsg, aiReply
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

    /** ✅ 클라이언트 IP 추출 유틸 */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank()) {
            ip = request.getRemoteAddr();
        }
        // 여러 IP가 있을 경우 첫 번째만 사용
        if (ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
