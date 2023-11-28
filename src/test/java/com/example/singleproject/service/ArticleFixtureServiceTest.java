package com.example.singleproject.service;

import com.example.singleproject.domain.Article;
import com.example.singleproject.dto.ArticleDto;
import com.example.singleproject.dto.ArticleWithCommentsDto;
import com.example.singleproject.dto.Type.SearchType;
import com.example.singleproject.fixture.ArticleFixture;
import com.example.singleproject.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleFixtureServiceTest {

    @InjectMocks
    private ArticleService articleService;
    @Mock
    private ArticleRepository articleRepository;


    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환함")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList(){
        // given

        // when
        Page<ArticleDto> articles = articleService.searchArticles(SearchType.TITLE,"java", Pageable.ofSize(0));

        // then
        assertNotNull(articles);
    }

    @DisplayName("검색된 게시글 리스트에서 게시글 한개 출력")
    @Test
    void givenArticleId_whenSearchingArticles_thenReturnsArticle(){
        // given


        // when
        ArticleWithCommentsDto articles = articleService.getArticle(1l);

        // then
        assertNotNull(articles);
    }

    @DisplayName("게시글 작성")
    @Test
    void givenArticleData_whenSavingArticles_thenReturnsArticle(){
        // given
        ArticleDto dto = ArticleFixture.get();      // test를 위해 미리 만들어준 샘플 dto 가져옴

        given(articleRepository.save(any(Article.class)))
                .willReturn(null);

        // when
        articleService.saveArticle(dto);

        // then
        then(articleRepository).should().save(any(Article.class));  // save 메서드가 한번 호출이 되었는지 확인
    }


}