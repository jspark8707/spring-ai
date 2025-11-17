package com.d2dev.springai.rag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Controller
@RequestMapping("/rag")
@RequiredArgsConstructor
public class RagController {

    private final RagService ragService;
    private final SpringTemplateEngine templateEngine;

    /** 메인 화면 **/
    @GetMapping
    public String dashboard() {
        return "rag/rag-dashboard";
    }

    /** PDF 업로드 (인덱싱용) **/
    @PostMapping("/upload")
    public String uploadPdf(@RequestParam("file") MultipartFile file, Model model) {
        boolean success = ragService.processPdf(file);
        model.addAttribute("uploadResult", success ? "✅ 업로드 완료" : "❌ 업로드 실패");
        return "rag/rag-dashboard";
    }

    /** RAG 질의 **/
    @PostMapping("/query")
    @ResponseBody
    public String query(@RequestParam String question, HttpServletRequest request) {

        RagResponse response = ragService.queryWithRag(question);

        // ✅ WebContext 대신 Context 사용 (템플릿 렌더링에 충분함)
        Context context = new Context(request.getLocale());
        context.setVariable("question", question);
        context.setVariable("answer", response.getAnswer());
        context.setVariable("sources", response.getSources());

        // ✅ rag-chat.html fragment를 HTML로 렌더링
        return templateEngine.process("rag/rag-chat", context);
    }

    }

