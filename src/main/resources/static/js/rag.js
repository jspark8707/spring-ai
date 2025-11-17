document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("#query-form");
    const chatArea = document.querySelector("#chat-area");
    const sidebar = document.querySelector(".sidebar");
    const header = document.querySelector(".top-header");
    const uploadForm = document.querySelector("#upload-form");
    const progressBar = document.querySelector("#progress-bar");
    const uploadStatus = document.querySelector("#upload-status");
    const fileInput = document.querySelector("#file-input");
    const dropArea = document.querySelector("#drop-area");
    const clearBtn = document.querySelector("#btn-clear");
    const uploadLabel = document.querySelector("#upload-label");
    const contextList = document.querySelector("#context-list"); // ✅ 우측 참고 문서 영역

    /* ===== 📱 모바일 메뉴 열기 ===== */
    if (header && sidebar) {
        header.addEventListener("click", () => {
            if (window.innerWidth <= 768) {
                sidebar.classList.toggle("active");
            }
        });
    }

    /* ===== 📂 PDF 업로드 (단일 파일 전용) ===== */
    if (uploadForm && fileInput && progressBar && uploadStatus && dropArea) {
        fileInput.addEventListener("change", (e) => {
            const file = e.target.files?.[0];
            if (file) {
                uploadStatus.textContent = `📄 ${file.name} 선택됨`;
                uploadLabel.textContent = "다른 파일을 선택하려면 클릭하세요";
            } else {
                uploadStatus.textContent = "⚠️ 파일이 선택되지 않았습니다.";
            }
        });

        ["dragenter", "dragover"].forEach((ev) =>
            dropArea.addEventListener(ev, (e) => {
                e.preventDefault();
                dropArea.style.background = "#eff6ff";
            })
        );

        ["dragleave", "drop"].forEach((ev) =>
            dropArea.addEventListener(ev, (e) => {
                e.preventDefault();
                dropArea.style.background = "#f3f4f6";
            })
        );

        dropArea.addEventListener("drop", (e) => {
            e.preventDefault();
            const dropped = e.dataTransfer?.files?.[0];
            if (dropped) {
                const dataTransfer = new DataTransfer();
                dataTransfer.items.add(dropped);
                fileInput.files = dataTransfer.files;
                uploadStatus.textContent = `📄 ${dropped.name} 선택됨`;
                uploadLabel.textContent = "다른 파일을 선택하려면 클릭하세요";
            }
        });

        uploadForm.addEventListener("submit", (e) => {
            e.preventDefault();
            const file = fileInput.files?.[0];
            if (!file) return alert("PDF 파일을 선택하세요.");

            const xhr = new XMLHttpRequest();
            xhr.open("POST", "/rag/upload");

            xhr.upload.onprogress = (event) => {
                if (event.lengthComputable) {
                    const percent = (event.loaded / event.total) * 100;
                    progressBar.style.width = percent + "%";
                }
            };

            xhr.onload = () => {
                const fileName = file?.name || "파일";
                if (xhr.status === 200) {
                    progressBar.style.width = "100%";
                    uploadStatus.textContent = `✅ ${fileName} 업로드 완료!`;
                    setTimeout(() => (progressBar.style.width = "0%"), 2000);
                } else {
                    uploadStatus.textContent = `⚠️ ${fileName} 업로드 실패 (${xhr.status})`;
                }
            };

            const formData = new FormData();
            formData.append("file", file);
            xhr.send(formData);
        });

        if (clearBtn) {
            clearBtn.addEventListener("click", () => {
                fileInput.value = "";
                uploadStatus.textContent = "🗑️ 파일이 삭제되었습니다.";
                uploadLabel.textContent = "클릭하거나 PDF를 끌어다 놓으세요";
                progressBar.style.width = "0%";
            });
        }
    }

    /* ===== 💬 RAG 질의 (Ajax + 우측 참고 문서 표시) ===== */
    if (form && chatArea) {
        form.addEventListener("submit", async (e) => {
            e.preventDefault();

            const formData = new FormData(form);
            const question = formData.get("question");

            if (!question.trim()) return;

            // ✅ 새 질문 시 우측 참고 문서 초기화
            if (contextList) {
                contextList.innerHTML =
                    `<p>AI가 인용한 문서 근거가 여기에 표시됩니다.</p>`;
            }

            // ✅ 사용자 질문 표시
            const userBubble = document.createElement("div");
            userBubble.className = "user-bubble";
            userBubble.textContent = question;
            chatArea.appendChild(userBubble);

            // ✅ 입력창 바로 비우기
            form.querySelector("input[name='question']").value = "";

            // ✅ AI 대기 말풍선
            const aiBubble = document.createElement("div");
            aiBubble.className = "ai-bubble";
            aiBubble.textContent = "🤖 인공지능이 답변을 준비 중입니다...";
            chatArea.appendChild(aiBubble);
            chatArea.scrollTop = chatArea.scrollHeight;

            try {
                const response = await fetch("/rag/query", {
                    method: "POST",
                    body: formData,
                    headers: { "X-Requested-With": "XMLHttpRequest" },
                });

                const html = await response.text();

                // ✅ 중앙 영역에 AI 답변 표시
                aiBubble.innerHTML = html;

                // ✅ 우측 참고 문서 표시
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, "text/html");
                const sources = doc.querySelector("#sources-content"); // rag-chat.html 내부 id
                if (sources && contextList) {
                    contextList.innerHTML = sources.innerHTML;
                }

                chatArea.scrollTop = chatArea.scrollHeight;
            } catch (err) {
                aiBubble.innerHTML = `<div class="ai-bubble" style="color:red;">⚠️ 서버 오류: ${err.message}</div>`;
            }
        });
    }
});


/* ===== 📏 우측 패널 리사이즈 ===== */
const contextPanel = document.getElementById("context-panel");
const resizeHandle = document.getElementById("resize-handle");

if (contextPanel && resizeHandle) {
    let isResizing = false;

    resizeHandle.addEventListener("mousedown", (e) => {
        isResizing = true;
        document.body.style.cursor = "col-resize";
        e.preventDefault();
    });

    document.addEventListener("mousemove", (e) => {
        if (!isResizing) return;
        const newWidth = window.innerWidth - e.clientX;
        if (newWidth >= 200 && newWidth <= 600) {
            contextPanel.style.width = `${newWidth}px`;
        }
    });

    document.addEventListener("mouseup", () => {
        if (isResizing) {
            isResizing = false;
            document.body.style.cursor = "default";
        }
    });
}
