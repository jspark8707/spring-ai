// ChatRequest.java
package com.d2dev.springai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {
    private String provider;  // "openai" or "ollama"
    private String message;
}
