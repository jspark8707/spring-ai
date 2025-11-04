package com.d2dev.springai.controller;

import com.d2dev.springai.AnswerResponse;
import com.d2dev.springai.ChatRequest;
import com.d2dev.springai.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public AnswerResponse chat(@RequestBody ChatRequest request) {
        return chatService.chat(request);
    }

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of("status", "ok", "service", "spring-ai");
    }
}
