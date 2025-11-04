package com.d2dev.springai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration;

@SpringBootTest
// ✅ 테스트 시 openai 자동구성 제외 (ollama만 로드)
@ImportAutoConfiguration(exclude = { OpenAiAutoConfiguration.class })
class SpringAiApplicationTests {

    @Test
    void contextLoads() {
        // 단순 컨텍스트 로딩 테스트
    }
}
