package com.d2dev.springai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KrimsManualIndexer {

    private final PgVectorStore pgVectorStore;

    /**
     * content: 인덱싱하려는 텍스트(이미 PDF에서 뽑아온 문자열 등)
     */
    public void indexDocument(String content) {

        // ✅ 1. 문서 객체 생성 (내용만 있으면 됨)
        Document doc = new Document(content);
        // 메타데이터가 필요하면 두 번째 파라미터로 Map<String, Object> 넣을 수 있음
        // Document doc = new Document(content, Map.of("source", "krims-manual"));

        // ✅ 2. 벡터스토어에 추가
        //    -> 여기서 PgVectorStore가 내부적으로 EmbeddingModel을 이용해
        //       임베딩 계산 + PGVector 테이블 저장까지 알아서 함
        pgVectorStore.add(List.of(doc));
    }
}
