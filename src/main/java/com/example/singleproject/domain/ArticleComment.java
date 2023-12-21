package com.example.singleproject.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)     // 부모 클래스에 있는 값까지 toString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleComment extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @ManyToOne(optional = false)    // 양방향 맵핑
    private Article article;      // FK (Article Entity Id)

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")    // 단방향 맵핑일때 어떤 id와 연관되는지 표시
    private UserAccount userAccount;

    @Setter
    @Column(nullable = false, length = 500)
    private String content;     // 내용

    protected ArticleComment() {
    }

    private ArticleComment(Article article, UserAccount userAccount,String content) {
        this.article = article;
        this.userAccount = userAccount;
        this.content = content;
    }

    public static ArticleComment of(Article article, UserAccount userAccount, String content){
        return new ArticleComment(article,userAccount,content);
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
