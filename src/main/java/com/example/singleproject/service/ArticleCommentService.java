package com.example.singleproject.service;

import com.example.singleproject.domain.Article;
import com.example.singleproject.domain.UserAccount;
import com.example.singleproject.dto.ArticleCommentDto;
import com.example.singleproject.repository.ArticleCommentRepository;
import com.example.singleproject.repository.ArticleRepository;
import com.example.singleproject.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId){
        return List.of();
    }

    public void saveArticleComment(ArticleCommentDto dto){
        try{
            Article article = articleRepository.getReferenceById(dto.articleId());
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());

            articleCommentRepository.save(dto.toEntity(article,userAccount));

        }catch (EntityNotFoundException e){
            log.warn("댓글 저장 실패. 댓글의 게시글을 찾을 수 없습니다. - {}", e.getLocalizedMessage());
        }
    }

    public void updateArticleComment(ArticleCommentDto dto){

    }

    public void deleteArticleComment(Long articleCommentId){

    }
}
