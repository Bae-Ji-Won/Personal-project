package com.example.singleproject.controller;

import com.example.singleproject.dto.Type.FormStatus;
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

    @GetMapping("/search-hashtag")
    public String searchHashtag(
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ){
        Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue,pageable).map(ArticleResponse::from);
        List<Integer> pagenums = pagenationService.getPagenationBarNumbers(pageable.getPageNumber(),articles.getTotalPages());
        List<String> hashtags = articleService.getHashtags();

        map.addAttribute("articles",articles);
        map.addAttribute("hashtags",hashtags);
        map.addAttribute("paginationBarNumbers", pagenums);
        map.addAttribute("searchType",SearchType.HASHTAG);

        return "articles/search-hashtag";
    }

    // 게시글 작성 폼 이동
    @GetMapping("/form")
    public String articleForm(ModelMap map) {
        map.addAttribute("formStatus", FormStatus.CREATE);

        return "articles/form";
    }

//    // 게시글 작성 폼 작성
//    @PostMapping("/form")
//    public String postNewArticle(
//            @AuthenticationPrincipal
//            BoardPrincipal boardPrincipal,
//            ArticleRequest articleRequest
//    ) {
//        articleService.saveArticle(articleRequest.toDto(boardPrincipal.toDto()));
//
//        return "redirect:/articles";
//    }

//    // 게시글 수정 폼(기존 값 출력)
//    @GetMapping("/{articleId}/form")
//    public String updateArticleForm(@PathVariable Long articleId, ModelMap map) {
//        ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));
//
//        map.addAttribute("article", article);
//        map.addAttribute("formStatus", FormStatus.UPDATE);
//
//        return "articles/form";
//    }
//
//    // 게시글 수정 폼(새로운 값 입력 받음)
//    @PostMapping("/{articleId}/form")
//    public String updateArticle(
//            @PathVariable Long articleId,
//            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
//            ArticleRequest articleRequest
//    ) {
//        articleService.updateArticle(articleId, articleRequest.toDto(boardPrincipal.toDto()));
//
//        return "redirect:/articles/" + articleId;
//    }
//
//    // 게시글 삭제
//    @PostMapping("/{articleId}/delete")
//    public String deleteArticle(
//            @PathVariable Long articleId,
//            @AuthenticationPrincipal BoardPrincipal boardPrincipal
//    ) {
//        articleService.deleteArticle(articleId, boardPrincipal.getUsername());
//
//        return "redirect:/articles";
//    }
}
