package com.example.singleproject.repository;

import com.example.singleproject.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}