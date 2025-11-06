package com.d2dev.springai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class SlackService {

    @Value("${slack.webhook-uri:}")   // ✅ YAML 키와 일치하도록 수정
    private String webhookUrl;

    @Value("${slack.enabled:false}")
    private boolean enabled;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendMessage(String message) {
        if (!enabled) {
            System.out.println("🚫 Slack 비활성화 상태 (개발/테스트 환경)");
            return;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> payload = Map.of("text", message);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload, headers);

            restTemplate.postForEntity(webhookUrl, entity, String.class);
            System.out.println("✅ Slack 메시지 전송 완료: " + message);
        } catch (Exception e) {
            System.err.println("❌ Slack 메시지 전송 실패: " + e.getMessage());
        }
    }
}
