package com.example.singleproject.dto.Type;

import lombok.Getter;

// 검색할때 미리 정해둔 카테고리 안에서만 지정할 수 있도록 함
public enum SearchType {
    TITLE("제목"), CONTENT("본문"), ID("유저 ID"), NICKNAME("닉네임"), HASHTAG("해시태그");

    @Getter
    private final String description;

    SearchType(String description){
        this.description = description;
    }
}
