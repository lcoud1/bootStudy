package org.zerock.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // 테이블 자동으로 관리
@Getter
@Builder    // builder 삽입 시에  @AllArgsConstructor, @NoArgsConstructor 필수
@AllArgsConstructor
@NoArgsConstructor
@ToString // 객체가 아닌 문자열로 변환
public class Guestbook extends BaseEntity{
    // BaseEntity를 상속받아서 시간을 동기화한다

    @Id // guestbook 테이블의 pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 마리아 db용 자동 번호 생성
    private Long gno; // 방명록에서 사용할 번호

    @Column(length = 100, nullable = false) // 문자 100, not null
    private String title; // 제목

    @Column(length = 1500, nullable = false) // 문자 1500, not null
    private String content; // 내용

    @Column(length = 50, nullable = false) // 문자 50, not null
    private String writer;  // 작성자

    public void changeTitle(String title){  // setter의 역할을 하는 제목 수정용 메서드
        this.title = title;
    }

    public void changeContent(String content){  // setter의 역할을 하는 내용 수정용 메서드
        this.content = content;
    }

}
