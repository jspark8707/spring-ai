# 생성형 AI 기반 연구지원 사업 방향성 정리

## 1. 사업 개요

### AS-IS (4차 사업)

기존 빅데이터 플랫폼 구축 완료

```text
KRI / KCI / IRIS / e-R&D
            │
            ▼
      ODS → DW → DM
```

주요 역할

* Tibero 기반 통합DB 구축
* 데이터 표준화 및 품질관리
* 통계·분석 서비스 제공

---

### TO-BE (26년 사업)

기존 통합DB를 AI가 활용할 수 있도록 확장

```text
[기존 통합DB]
ODS → DW → DM

        ↓

[AI 확장영역]
AI Data Pipeline

        ↓

Metadata Repository
(메타데이터 관리체계)

        ↓

Elasticsearch
(Document + Metadata + Vector)

        ↓

RAG

        ↓

sLLM

        ↓

AI 업무서비스
```

---

## 2. 기존 사업과 AI 사업의 차이

| 구분   | 기존 빅데이터 사업    | 생성형 AI 사업             |
| ---- | ------------- | --------------------- |
| 목적   | 데이터 통합 및 분석   | AI 활용 및 지식서비스         |
| 저장소  | ODS / DW / DM | ODS / DW / DM + AI 계층 |
| 검색방식 | SQL, 통계조회     | 의미검색(Semantic Search) |
| 결과   | 통계·분석정보       | AI 답변 및 추천            |
| 핵심기술 | ETL           | AI Data Pipeline      |

---

## 3. AI Data Pipeline 개념

기존 ETL을 대체하는 것이 아니라 AI 서비스를 위한 신규 파이프라인 추가

### 기존 ETL

```text
Extract
 ↓
Transform
 ↓
Load
```

### AI Data Pipeline

```text
Extract
 ↓
Transform
 ↓
Document
 ↓
Chunk
 ↓
Embedding
 ↓
Index
```

---

## 4. 왜 의미검색이 필요한가?

### DW 데이터 예시

```text
과제명
: 생성형 AI 기반 연구지원 플랫폼

초록
: 대규모 언어모델(LLM)을 활용한
  연구행정 지원 서비스 개발
```

### 기존 SQL 검색

```sql
SELECT *
FROM PROJECT
WHERE TITLE LIKE '%생성형 AI%'
```

정확한 문자열 검색은 가능

---

### 사용자 질문

```text
"LLM 관련 연구과제 알려줘"
```

기존 SQL

❌ 검색 어려움

---

### AI 검색

```text
생성형 AI
LLM
AI Assistant
대규모 언어모델
```

의미적 유사성 기반 검색 가능

---

## 5. Metadata 관리체계가 중요한 이유

### NRF 데이터 특성

```text
과제 · 연구자 · 성과 · 평가
규정 · RFP · 사업정보
```

등 다양한 유형의 데이터가 혼재

---

따라서 단순 Vector Search보다

```text
출처, 연도, 사업, 기관
보안등급, 문서유형, 버전
```

등의 Metadata 관리가 중요

---

### Metadata 예시

```json
{
  "source":"KRI",
  "type":"과제",
  "year":"2026",
  "program":"중견연구"
}
```

Metadata 역할

* 문서 출처 관리
* 문서 유형 관리
* 사업 정보 관리
* 보안등급 관리
* 버전 관리
* 임베딩 버전 관리
* 재임베딩 관리
* 검색 필터링 지원

---

## 6. 예상 TO-BE 구조

```text
[기존 빅데이터 플랫폼]

KRI / KCI / IRIS / e-R&D
            │
            ▼
      ODS → DW → DM

       (기존 구축 완료)

──────────────────────────────────

      생성형 AI 사업 (신규)

            │
            ▼

      AI Data Pipeline
   (Document / Chunk /
    Embedding / Index)

            │
            ▼

 Metadata Repository
 (메타데이터 관리체계)

            │
            ▼

 Elasticsearch
(Document + Metadata + Vector)

            │
            ▼

           RAG

            │
            ▼

          sLLM

            │
            ▼

      AI 업무서비스

- RFP 작성 지원
- 평가위원 추천
- 규정 챗봇
- 평가의견 요약
- 유사과제 분석
```

---

## 7. 핵심 메시지

### 4차 사업

```text
ODS / DW / DM 기반 통합DB 구축
```

### 26년 생성형 AI 사업

```text
기존 통합DB
+
AI Data Pipeline
+
Metadata 관리체계
+
Elasticsearch
+
RAG
+
sLLM
```

---

## 결론

본 사업은 통합DB를 새로 구축하는 사업이 아니다.

기 구축된 ODS, DW, DM 체계를 활용하여

* AI Data Pipeline
* Metadata 관리체계
* Elasticsearch 기반 검색계층
* RAG
* sLLM

을 추가 구축함으로써 연구행정 특화 생성형 AI 플랫폼으로 확장하는 사업으로 해석할 수 있다.
