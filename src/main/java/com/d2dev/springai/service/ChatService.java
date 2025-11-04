package com.d2dev.springai.service;

import com.d2dev.springai.AnswerResponse;
import com.d2dev.springai.ChatRequest;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChatService {

    private final ChatModel openAiChatModel;
    private final ChatModel ollamaChatModel;

    public ChatService(
            @Qualifier("openAiChatModel") ChatModel openAiChatModel,
            @Qualifier("ollamaChatModel") ChatModel ollamaChatModel) {
        this.openAiChatModel = openAiChatModel;
        this.ollamaChatModel = ollamaChatModel;
    }

    public AnswerResponse chat(ChatRequest request) {
        ChatModel model = request.getProvider().equalsIgnoreCase("openai")
                ? openAiChatModel
                : ollamaChatModel;

        Prompt prompt = new Prompt(request.getMessage());
        ChatResponse response = model.call(prompt);

        return new AnswerResponse(request.getProvider(),
                response.getResult().getOutput().getContent());
    }
}

