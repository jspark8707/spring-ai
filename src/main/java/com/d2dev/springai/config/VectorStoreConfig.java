package com.d2dev.springai.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class VectorStoreConfig {

    private final JdbcTemplate jdbcTemplate;

    @Bean
    public PgVectorStore pgVectorStore(@Qualifier("ollamaEmbeddingModel") EmbeddingModel embeddingModel) {
        // ⚠️ 생성자 순서 중요: (JdbcTemplate, EmbeddingModel)
        return new PgVectorStore(jdbcTemplate, embeddingModel);
    }
}
