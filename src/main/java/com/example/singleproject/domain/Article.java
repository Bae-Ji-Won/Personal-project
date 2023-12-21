package com.example.singleproject.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)            // 필수 연관(null 불가)
    @JoinColumn(name = "userId")            // 단방향 맵핑일때 어떤 id와 연관되는지 표시
    private UserAccount userAccount;

    @Setter
    @Column(nullable = false)
    private String title;   // 제목       Setter를 개별 변수에 걸어 외부에서 id에는 접근하지 못하도록 함

    @Setter
    @Column(nullable = false, length = 10000)
    private String content;     // 내용
    @Setter private String hashtag;     // 해시태그

    @ToString.Exclude           // 순환 참조를 방지하기 위해 여기서는 ToString을 끊어줌 (Article -> ArticleComment -> Article 무한 반복됙 때문에)
    @OrderBy("createdAt DESC")      // id기준으로 정렬
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)   // 양방향 맵핑 ArticleComment의 articleId의 변수를 가진 것과 맵핑함
    private final Set<ArticleComment> articleComments= new LinkedHashSet<>();


    protected Article() {}      // 생성자1

    private Article(UserAccount userAccount, String title, String content, String hashtag) {     // 생성자2
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {           // Dto -> Entity할떄 사용
        String hashtagResult = "";
        if(hashtag.charAt(0) != '#'){
            hashtagResult = String.format('#'+hashtag);
        }
        return new Article(userAccount, title,content,hashtagResult);
    }



    /* equals && hashCode는 현재 Entity와 DB에 저장된 테이블이 일치하는지 매칭검사하는 용으로 여러개의 컬럼들 중
       유니크 값을 가지는 id만 비교해도 됨(만약 DB에서 content내용을 변경한 경우는 찾을수가 없기때문에 그때는 Lombok의 @EqualsAndHashCode어노테이션을 클래스에 추가해줘서 모든 컬럼에 대해 비교시킴)
     */
    @Override
    // 동등성 검사(영속화 검사 DB와 Entity가 1:1 매핑이 잘 되어 있는지 확인)
    public boolean equals(Object o) {                                   // 객체를 입력받음
        if (this == o) return true;                                     // 객체의 참조가 같은지 비교
        if (o == null || getClass() != o.getClass()) return false;      // o가 null이거나 o의 클래스가 현재 클래스랑 다르다면 false 반환
        Article article = (Article) o;                                  // 위의 경우가 아니라면 o객체를 Article객체로 캐스팅함
        return id != null && id.equals(article.id);                     // 현재 id값이 무조건 있는 상태에서 현재 id와 방금전 캐스팅한 객체의 id값을 비교햐서 맞으면 true, 틀리면 false 반환
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
