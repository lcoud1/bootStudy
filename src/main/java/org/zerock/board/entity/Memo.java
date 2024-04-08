package org.zerock.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity                     // 해당 클래스가 엔티티 역할 담당
@Table(name= "tbl_memo")    // db 테이블 명 지정
@ToString                   // 객체를 문자열로 변경
@Getter                     // 게터
@Builder                    // 메서드, 필드(값).필드(값).builder; / 빌더 패턴
                            // 빌더 패턴 사용 시 (@AllArgsConstructor, @NoArgsConstructor) 필수
@AllArgsConstructor         // 모든 매개값을 같는 생성자, new 클래스 (모든 필드 값 파라미터로 만듬)
@NoArgsConstructor          // 매개값이 없는 생성자, new 클래스();
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 스트래터지(방법,계략)
    // auto : JPA 구현체가 생성 방식을 결정
    // IDENTITY : MariaDB용 (auto increment)
    // SEQUENCE : Oracle용 (@SequenceGenerator와 같이 사용)
    // TABLE : 키생성 전용 테이블을 생성해 키 생성(@TableGenerator와 같이 사용)
    // 엔티티 = 데이터베이스에 테이블과 필드를 생성시켜 관리하는 객체
    // 엔티티를 이용해서 jpa를 활성화 시키려면 application.properties에 필수 항목 추가
    private Long mno ;

    @Column(length = 200, nullable = false) // 추가 열 설정 (글자길이 200 , 널허용 여부, not null 처리
    private String memoText;

    //    Hibernate:
    //    create table tbl_memo (
    //    mno bigint not null auto_increment,
    //    memo_text varchar(200) not null,
    //    primary key (mno)
    //    ) engine=InnoDB

}