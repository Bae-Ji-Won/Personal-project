package com.example.singleproject.controller;

import com.example.singleproject.dto.UserAccountDto;
import com.example.singleproject.dto.request.ArticleCommentRequest;
import com.example.singleproject.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/comments")
@Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest){

        articleCommentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of(
                "test","pw","test@naver.com",null,null
        )));

        return "redirect:/articles" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId,Long articleId) {
        articleCommentService.deleteArticleComment(commentId);

        return "redirect:/articles" + articleId;
    }
}
