package com.example.singleproject.repository;

import com.example.singleproject.config.JpaConfig;
import com.example.singleproject.domain.Article;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)            // JpaConfig는 내가 직접 설정한 파일이므로 import를 해줘야 오류가 발생하지 않고 내가 원하는 값을 얻을 수 있음
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;

    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository,@Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }



    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine(){
        long count = articleRepository.count();
        System.out.println(count);

        Article article = articleRepository.findById(1L).orElse(null);

        assertEquals("test", article.getTitle());

//        assertThat(count)
//                .isEqualTo(0);
    }


}