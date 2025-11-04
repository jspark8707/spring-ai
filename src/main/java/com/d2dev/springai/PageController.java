package com.d2dev.springai;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String chatPage() {
        return "chat"; // "chat.html" 파일을 찾아 렌더링하라는 의미
    }
}