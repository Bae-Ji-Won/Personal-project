package com.example.singleproject.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayName("View Controller - 게시글")
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mvc;

    public ArticleControllerTest(@Autowired MockMvc mockMvc){
        this.mvc = mockMvc;
    }


    @DisplayName("[GET] 게시글 리스트 페이지 - success")
    @Test
    public void ArticleBoardList_Success() throws Exception {
        // Given

        // When & Then
        mvc.perform(MockMvcRequestBuilders.get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"));
    }


    @DisplayName("[GET] 게시글 상세 페이지 - success")
    @Test
    public void ArticleBoard_Success() throws Exception {
        // Given

        // When & Then
        mvc.perform(MockMvcRequestBuilders.get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"));
    }


    @DisplayName("[GET] 게시글 검색 전용 페이지 - success")
    @Test
    public void ArticleSearchBoard_Success() throws Exception {
        // Given

        // When & Then
        mvc.perform(MockMvcRequestBuilders.get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("articles/search"))
                .andExpect(content().contentType(MediaType.TEXT_HTML));
    }


    @DisplayName("[GET] 게시글 해시태그 검색 페이지 - success")
    @Test
    public void ArticleHashtagSearchBoard_Success() throws Exception {
        // Given

        // When & Then
        mvc.perform(MockMvcRequestBuilders.get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(view().name("articles/search-hashtage"))
                .andExpect(content().contentType(MediaType.TEXT_HTML));
    }
}