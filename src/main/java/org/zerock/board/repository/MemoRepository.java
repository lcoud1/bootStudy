package org.zerock.board.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Memo;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    // extends JpaRepository<엔티티명, PK 타입>

    // JPA에서 C,R,U,D,를 담당한다. 다해줌
    // JpaRepository 내장된 메서드

    // insert 작업 : save(엔티티 객체)
    // select 작업 : findById(키 타입), getOne(키 타입)
    // update 작업 : save(엔티티 객체)
    // delete 작업 : deleteById(키 타입),  delete(엔티티 객체)

    // 쿼리 메서드 = 메서드 명이 쿼리를 대체함
    // https://docs.spring.io/spring-data/jpa/docs/current-SNAPSHOT/reference/html/#jpa.query-methods -> 레퍼런스 문서 사이트
    // https://docs.spring.io/spring-data/jpa/docs/current-SNAPSHOT/reference/html/#jpa.query-methods.query-creation
    
    // List<Memo> 리턴 타입 -> 리스트 타입에 객체는 memo
    // 매개값으로 받은 from ~ to 까지 select 진행하여 list로 리턴하는 쿼리 메서드
    // 단지 변수명일 뿐
    // from부터 to까지 = ex) 1번부터 6번까지
    List<Memo> findByMnoBetweenOrderByDesc(Long from, Long to);

    // Page<Memo> 리턴 타입 -> 페이징 타입의 객체는 memo
    // 매개값으로 받은 from ~ to 까지 select를 진행하여 페이징 타입으로 리턴하는 쿼리 메서드
    Page<Memo> findByMnoBetween (Long from, Long to, Pageable pageable);

    // ex) 10보다 작은 데이터를 삭제한다
    void deleteMemoByMnoLessThan(Long num);

    // @Query는 순수한 sql 쿼리문으로 작성, 단 테이블명이 아닌 엔티티명으로 사용
    // 쿼리문은 대문자로 쓰자!
    @Query("SELECT m FROM Memo m ORDER BY m.mno DESC ")
    List<Memo> getListDesc();   // 내가 만든 메서드명 -> 메서드 실행 시 위에 있는 쿼리문 실행

    // 매개값이 있는 @Query문 :값 (타입으로 받음)
    // 값 대소문자 다 맞추기 / "" <- 안에 있는 것들
    @Query("UPDATE Memo m SET m.memoText = :memoText WHERE m.mno = :mno")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);

    // 매개값이 객체(빈)으로 들어올 경우
    @Query("UPDATE Memo m SET m.memoText = : #{memoBean.memoText} WHERE m.mno= : #{memoBean.mno}")
    int updateMemoBean(@Param("memobean") Memo memo);

    // @Query 메서드로 페이징 처리 해보기 -> 리턴 타입이 page<Memo>
    @Query(value = "SELECT m FROM Memo m WHERE m.mno > :mno",
            countQuery = "SELECT count(m) FROM Memo m WHERE m.mno > : mno")
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);

    // db에 존재하지 않은 값을 처리 해보기 : ex) 날짜
    @Query(value = "SELECT m.mno, m.memoText, CURRENT_DATE FROM Memo m WHERE m.mno > : mno",
           countQuery = "SELECT count(m) FROM Memo m WHERE m.mno > : mno")
    Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);

    // Native Sql 처리 : DB용 쿼리로 사용하는 기법
    @Query(value = "SELECT * from memo WHERE mno > 0", nativeQuery = true)
    List<Object> getNativeResult();

}
