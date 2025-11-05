package com.d2dev.springai.controller;

import com.d2dev.springai.service.KrimsManualIndexer;
import com.d2dev.springai.service.KrimsRagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/krims")
public class KrimsController {

    private final KrimsRagService ragService;
    private final KrimsManualIndexer indexer; // ✅ 색인 서비스 주입

    // ✅ 1. 질의 (기존)
    @PostMapping("/query")
    public Map<String, String> query(@RequestBody Map<String, String> req) {
        String question = req.getOrDefault("question", "");
        String answer = ragService.query(question);
        return Map.of("question", question, "answer", answer);
    }

    // ✅ 2. 색인 API (신규)
    @PostMapping("/index")
    public Map<String, String> index(@RequestBody Map<String, String> req) {
        String content = req.getOrDefault("content", "");
        if (content.isBlank()) {
            return Map.of("status", "error", "message", "content 값이 비어있습니다.");
        }

        try {
            indexer.indexDocument(content);
            return Map.of("status", "success", "message", "인덱싱 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("status", "error", "message", e.getMessage());
        }
    }
}
