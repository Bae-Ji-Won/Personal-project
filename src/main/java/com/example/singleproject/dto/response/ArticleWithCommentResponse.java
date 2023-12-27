package com.example.singleproject.dto.response;

import com.example.singleproject.dto.ArticleWithCommentsDto;
import com.example.singleproject.dto.HashtagDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleWithCommentResponse(
        Long id,
        String title,
        String content,
        Set<String> hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname,
        Set<ArticleCommentResponse> articleCommentsResponse
) implements Serializable {

    public static ArticleWithCommentResponse of(Long id, String title, String content, Set<String> hashtag, LocalDateTime createdAt, String email, String nickname, Set<ArticleCommentResponse> articleCommentsResponse) {
        return new ArticleWithCommentResponse(id, title, content, hashtag, createdAt, email, nickname, articleCommentsResponse);
    }

    public static ArticleWithCommentResponse from(ArticleWithCommentsDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return new ArticleWithCommentResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtagDtos().stream()
                        .map(HashtagDto::hashtagName)
                        .collect(Collectors.toUnmodifiableSet()),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname,
                dto.articleCommentDtos().stream()
                        .map(ArticleCommentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

}