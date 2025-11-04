package com.d2dev.springai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KrimsRagService {

    // ✅ 명시적으로 Ollama 임베딩 모델 지정
    private final @Qualifier("ollamaEmbeddingModel") EmbeddingModel embeddingModel;

    private final PgVectorStore pgVectorStore;

    // ✅ Ollama ChatModel 지정
    private final @Qualifier("ollamaChatModel") ChatModel chatModel;

    public String query(String question) {
        List<Document> results = pgVectorStore.similaritySearch(question);

        String context = results.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n"));

        String promptText = String.format("""
                다음은 검색된 문서 내용입니다:
                %s

                위 내용을 참고해서 다음 질문에 대답해주세요:
                %s
                """, context, question);

        ChatResponse response = chatModel.call(new Prompt(promptText));

        return response.getResult().getOutput().getContent();
    }
}
