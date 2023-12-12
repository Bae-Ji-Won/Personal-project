package com.example.singleproject.dto;

import groovy.transform.builder.Builder;

@Builder
public record ArticleUpdateDto(
        String title,
        String content,
        String hashtag
){
    public static ArticleUpdateDto of(String title, String content, String hashtag) {
        return new ArticleUpdateDto(title,content,hashtag);
    }
}