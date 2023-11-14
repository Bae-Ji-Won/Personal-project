package com.example.singleproject.domain;

import java.time.LocalDateTime;

public class ArticleComment {
    private Long id;
    private Article articleId;      // FK (Article Entity Id)

    private String content;     // 내용
    private LocalDateTime createdAt;    // 생성일자
    private String createdBy;       // 생성자
    private LocalDateTime modifiedAt;       // 수정일자
    private String modifiedBy;      // 수정자
}