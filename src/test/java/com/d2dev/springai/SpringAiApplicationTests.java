package com.d2dev.springai;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SpringAiApplicationTests {

    // ✅ ChatModel Bean을 Mock으로 대체해서 테스트 통과
    @MockBean
    private ChatModel chatModel;

    @Test
    void contextLoads() {
        // 단순 컨텍스트 로드 테스트
    }
}
