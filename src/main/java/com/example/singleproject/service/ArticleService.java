package com.example.singleproject.service;

import com.example.singleproject.domain.Article;
import com.example.singleproject.dto.ArticleDto;
import com.example.singleproject.dto.ArticleWithCommentsDto;
import com.example.singleproject.dto.Type.SearchType;
import com.example.singleproject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if(searchKeyword == null || searchKeyword.isBlank()){
            return articleRepository.findAll(pageable).map(ArticleDto::from);       // entity -> dto
        }

        return switch (searchType){
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag("#" + searchKeyword, pageable).map(ArticleDto::from);       // #은 고정값으로 두고 무조건 #으로 시작하도록 함
        };
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다. - articleId :"+articleId));
    }

    public void saveArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity(dto.userAccountDto().toEntity()));
    }

    public void updateArticle(ArticleDto dto){
        try {
            Article article = articleRepository.getReferenceById(dto.id());
            if(dto.title() != null){article.setTitle(dto.title());}
            if(dto.content() != null){article.setContent(dto.content());}
            article.setHashtag(dto.hashtag());

            // articleRepository.save(article);          영속성 @Transactional 특성으로 인해 내용 변경을 자동감지하여 저장됨 따라서, 별도로 저장을 해주지 않아도 됨
        }catch (EntityNotFoundException e){
            log.warn("게시글 업데이트 실패, 게시글을 찾을 수 없다 - dto:{}",dto);
        }

    }

    public void deleteArticle(long articleId){
        articleRepository.deleteById(articleId);
    }

    public long getArticleCount(){
        return articleRepository.count();
    }

    // 해시태그를 통해서 해당 해시태그 데이터 찾기
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable){
        if(hashtag == null || hashtag.isBlank()){
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtag(hashtag,pageable).map(ArticleDto::from);
    }

    // 해시태그 종류만 찾기
    public List<String> getHashtags(){
        return articleRepository.findAllDistinctHashtags();
    }
}
