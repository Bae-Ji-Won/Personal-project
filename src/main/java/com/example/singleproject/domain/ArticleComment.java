package com.example.singleproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @ManyToOne(optional = false)
    private Article articleId;      // FK (Article Entity Id)

    @Setter
    @Column(nullable = false, length = 500)
    private String content;     // 내용
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;    // 생성일자
    @CreatedBy
    @Column(nullable = false, length = 100)
    private String createdBy;       // 생성자
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;       // 수정일자
    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy;          // 수정자

    protected ArticleComment() {
    }

    private ArticleComment(Article articleId, String content) {
        this.articleId = articleId;
        this.content = content;
    }

    public static ArticleComment of(Article article, String content){
        return new ArticleComment(article,content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleComment that = (ArticleComment) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
