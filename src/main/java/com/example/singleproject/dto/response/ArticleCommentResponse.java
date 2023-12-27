package com.example.singleproject.dto.response;

import com.example.singleproject.dto.ArticleCommentDto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleCommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname,
        String userId
) implements Serializable {

    public static ArticleCommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId) {
        return ArticleCommentResponse.of(id, content, createdAt, email, nickname, userId);
    }


//    public static ArticleCommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId, Long parentCommentId) {
//        Comparator<ArticleCommentResponse> childCommentComparator = Comparator
//                .comparing(ArticleCommentResponse::createdAt)
//                .thenComparingLong(ArticleCommentResponse::id);
//        return new ArticleCommentResponse(id, content, createdAt, email, nickname, userId, parentCommentId, new TreeSet<>(childCommentComparator));
//    }

    public static ArticleCommentResponse from(ArticleCommentDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return ArticleCommentResponse.of(
                dto.id(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname,
                dto.userAccountDto().userId()
        );
    }

}