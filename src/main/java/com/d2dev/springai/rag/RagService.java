package com.d2dev.springai.rag;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RagService {

    private final PgVectorStore pgVectorStore;
    private final ChatModel chatModel;
    private final EmbeddingModel embeddingModel;

    /** ✅ PDF 업로드 처리 */
    public boolean processPdf(MultipartFile file) {
        try {
            // 1️⃣ PDF 텍스트 추출
            String text = extractTextFallback(file);

            // 2️⃣ 벡터 변환
            List<Document> docs = splitToDocuments(text, file.getOriginalFilename());
            pgVectorStore.add(docs);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** ✅ PDF → 텍스트 변환 (PDFBox 안정 버전) */
    private String extractTextFallback(MultipartFile file) throws IOException {
        try (PDDocument document = org.apache.pdfbox.Loader.loadPDF(file.getBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }


    /** ✅ 문서 분할 */
    private List<Document> splitToDocuments(String text, String fileName) {
        List<Document> docs = new ArrayList<>();
        int chunkSize = 800;
        for (int i = 0; i < text.length(); i += chunkSize) {
            int end = Math.min(i + chunkSize, text.length());
            String chunk = text.substring(i, end);
            Document doc = new Document(chunk);
            doc.getMetadata().put("source", fileName);
            docs.add(doc);
        }
        return docs;
    }

    /** ✅ RAG 질의 */
    public RagResponse queryWithRag(String question) {
        try {
            // 1️⃣ 유사 문서 검색
            List<String> relatedDocs = pgVectorStore.similaritySearch(question)
                    .stream()
                    .map(Document::getContent)
                    .toList();

            // 2️⃣ 문맥 조합
            String context = String.join("\n", relatedDocs);

            // 3️⃣ 프롬프트 구성
            PromptTemplate template = new PromptTemplate("""
                    다음 문서를 참고하여 질문에 답해주세요.

                    문맥:
                    {context}

                    질문:
                    {question}
                    """);

            Prompt prompt = template.create(Map.of(
                    "context", context,
                    "question", question
            ));

            // 4️⃣ LLM 호출
            var chatResponse = chatModel.call(prompt);
            String answer = chatResponse.getResult().getOutput().getContent();

            return new RagResponse(answer, relatedDocs);

        } catch (Exception e) {
            e.printStackTrace();
            return new RagResponse("⚠️ AI 응답 중 오류 발생: " + e.getMessage(), List.of());
        }
    }
}
