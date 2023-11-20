package com.example.singleproject.repository;

import com.example.singleproject.domain.Article;
import com.example.singleproject.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource

// QuerydslPredicateExecutor<Article>는 Article Entity안에 있는 모든 필드에 대한 기본 Query문을 제공
// QuerydslBinderCustomizer<QArticle>는 Article Entity안에 있는 모든 필드에 대한 커스텀 Query문을 만들기 위해 사용
// 만약 QuerydslBinderCustomizer를 사용하지 않았다면 검색기능에서 정확하게 값을 입력해야함. 그러나 사용한다면 대소문자 상관없고 앞글자 몇개만 입력해도 해당되는 값을 모두 찾아줌
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>,
        QuerydslBinderCustomizer<QArticle> {
    @Override
    default void customize(QuerydslBindings bindings, QArticle root){
        bindings.excludeUnlistedProperties(true);       // 선택적으로 검색기능을 사용하기 위해 true
        bindings.including(root.title,root.content,root.hashtag,root.createdAt,root.createdBy);     // 선택적 검색기능을 위한 필드 범위 정함
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);      // title에 대한 세부 검색기능 설정  => like '%${v}%'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}