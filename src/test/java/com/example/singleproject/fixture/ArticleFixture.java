package com.example.singleproject.fixture;

import com.example.singleproject.dto.ArticleDto;
import groovy.transform.builder.Builder;

import java.time.LocalDateTime;

@Builder
public class ArticleFixture {
    public static ArticleDto get() {
        return ArticleDto.of(
                LocalDateTime.now(),"kim","title","content","java"
        );
    }
}
