package com.d2dev.springai.controller;

import com.d2dev.springai.service.KrimsManualIndexer;
import com.d2dev.springai.service.KrimsRagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/krims")
public class KrimsController {

    private final KrimsRagService ragService;

    @PostMapping("/query")
    public Map<String, String> query(@RequestBody Map<String, String> req) {
        String question = req.getOrDefault("question", "");
        String answer = ragService.query(question);
        return Map.of("question", question, "answer", answer);
    }
}
