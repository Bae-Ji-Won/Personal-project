package com.example.singleproject.domain;


import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


// Entity에서 중복으로 사용하는 컬럼을 따로 클래스로 빼서 상속받아 사용하는 식으로 사용
// Auditing을 통해 작성되는 값을 별도로 구분함
@ToString
@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass       // 상속 관계에 있는 엔티티 클래스들 간에 공통으로 사용되는 매핑 정보를 정의
public class AuditingFields {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)        // 공백불가, 업데이트 불가
    private LocalDateTime createdAt;    // 생성일자

    @CreatedBy
    @Column(nullable = false, length = 100, updatable = false)
    private String createdBy;       // 생성자

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;       // 수정일자

    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy;          // 수정자
}
