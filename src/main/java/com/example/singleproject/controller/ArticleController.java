package com.example.singleproject.controller;

import com.example.singleproject.dto.Type.SearchType;
import com.example.singleproject.dto.response.ArticleResponse;
import com.example.singleproject.dto.response.ArticleWithCommentResponse;
import com.example.singleproject.service.ArticleService;
import com.example.singleproject.service.PagenationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/articles")
@Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleService articleService;
    private final PagenationService pagenationService;

    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,              // required = false => 필수로 받아오는 값이 아님
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ){

        Page<ArticleResponse> articles = articleService.searchArticles(searchType,searchValue,pageable).map(ArticleResponse::from);
        List<Integer> pagenums = pagenationService.getPagenationBarNumbers(pageable.getPageNumber(),articles.getTotalPages());

        map.addAttribute("articles",articles);
        map.addAttribute("paginationBarNumbers", pagenums);
        map.addAttribute("searchTypes",SearchType.values());

        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId,ModelMap map){
        ArticleWithCommentResponse article = ArticleWithCommentResponse.from(articleService.getArticle(articleId));
        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentsResponse());

        System.out.println("test : ");
        System.out.println(article.articleCommentsResponse().toString());
        return "articles/detail";
    }

}
