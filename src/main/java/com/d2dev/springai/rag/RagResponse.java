package com.d2dev.springai.rag;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class RagResponse {
    private String answer;
    private List<String> sources;
}
