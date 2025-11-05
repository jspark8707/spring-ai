package com.d2dev.springai.controller;

import com.d2dev.springai.AnswerResponse;
import com.d2dev.springai.ChatRequest;
import com.d2dev.springai.service.ChatService;
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
        return chatService.chat(request);
    }

    /** ✅ 헬스체크용 API */
    @GetMapping("/api/chat/ping")
    @ResponseBody
    public Map<String, String> ping() {
        return Map.of("status", "ok", "service", "spring-ai");
    }
}
