package com.d2dev.springai.config;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ModelConfig {

    /** ✅ Ollama ChatModel을 기본으로 지정 */
    @Bean
    @Primary
    public ChatModel primaryChatModel(@Qualifier("ollamaChatModel") ChatModel chatModel) {
        return chatModel;
    }

    /** ✅ Ollama EmbeddingModel을 기본으로 지정 */
    @Bean
    @Primary
    public EmbeddingModel primaryEmbeddingModel(@Qualifier("ollamaEmbeddingModel") EmbeddingModel embeddingModel) {
        return embeddingModel;
    }

}
