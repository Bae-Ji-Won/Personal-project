package com.example.singleproject.dto;

import com.example.singleproject.domain.Article;
import com.example.singleproject.domain.UserAccount;
import groovy.transform.builder.Builder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record ArticleDto(
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        Set<HashtagDto> hashtagDtos,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy

){

    public static ArticleDto of(UserAccountDto userAccountDto, String title, String content, Set<HashtagDto> hashtagDtos) {
        return new ArticleDto(null, userAccountDto, title, content, hashtagDtos, null, null, null, null);
    }

    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, Set<HashtagDto> hashtagDtos, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtagDtos, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    // Entity -> Dto
    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag().stream()
                        .map(HashtagDto::from)
                        .collect(Collectors.toUnmodifiableSet()),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    // Dto -> Entity 
    // 이런식으로 작성하면 Entity에서는 Dto의 존재를 몰라도 되어서 Entity에서는 오직 column에 대한 정보만 작성 가능
    // 또한, DTO에서 데이터에 문제가 발생해도 즉시 Entity에는 영향을 끼지기 어려움
    public Article toEntity(UserAccount userAccount) {
        return Article.of(
                userAccount,
                title,
                content
        );
    }
}