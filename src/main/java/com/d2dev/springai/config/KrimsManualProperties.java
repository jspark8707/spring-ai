package com.d2dev.springai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "krims.manual")
public class KrimsManualProperties {

    /**
     * KRIMS 매뉴얼 PDF 파일 경로 (classpath 또는 외부 상대경로)
     * 예: classpath:data/krims-manual-v1.0.pdf
     */
    private String pdfPath;

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }
}
