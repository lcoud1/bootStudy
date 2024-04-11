package org.zerock.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass   // 직접 테이블용은 아님을 명시
@Getter
@EntityListeners(value = { AuditingEntityListener.class} )
// setter 대신 감시용 코드 : 데이터 변경을 감지하여 변경 시에 적용 시키는 역할 --> Main 메서드에 추가 코드 필수
abstract class BaseEntity {
    // abstract 상속 간에 추상 클래스 동작
    // 테이블의 공통된 부분을 상속해줄 클래스 ex) 게시글 등록 시간, 제목 등등

    @CreatedDate    // 게시물 생성 시에 동작 -> create시에 반응을 entitylistner가 함
    @Column(name = "regdate", updatable = false)    // db 테이블에 필드명을 지정, 업데이터 안되게 강제로 지정
    private LocalDateTime regDate;  // 게시물 등록 시간

    @LastModifiedDate   // 게시물 수정 시에 동작
    @Column(name = "moddate")   // db 테이블에 필드명을 지정
    private LocalDateTime modDate; // 게시물 수정 시간

}
