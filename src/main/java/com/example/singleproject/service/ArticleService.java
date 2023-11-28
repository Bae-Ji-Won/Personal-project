package com.example.singleproject.service;

import com.example.singleproject.dto.ArticleDto;
import com.example.singleproject.dto.ArticleWithCommentsDto;
import com.example.singleproject.dto.Type.SearchType;
import com.example.singleproject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(long articleId) {
        return null;
    }

    public void saveArticle(ArticleDto dto) {
    }

    public void updateArticle(ArticleDto dto){

    }

    public void deleteArticle(long articleId){

    }
}
