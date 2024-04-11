package org.zerock.board.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.board.entity.Guestbook;
import org.zerock.board.entity.QGuestbook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest // 스프링부트 테스트용
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies(){
        // 더미 데이터 삽입 테스트

        IntStream.rangeClosed(1, 300).forEach(i ->{

            Guestbook guestbook = Guestbook.builder()
                    .title("제목" + i)
                    .content("내용" + i)
                    .writer("작성자" + (i % 10))   // user0 ~ user30
                    .build();   // 빌터 패턴은 entity에 @Builder 필수!
            System.out.println(guestbookRepository.save(guestbook));
            // jpa에 save 메서드를 실행하면서 출력까지 동시에 진행
        });

    }

    @Test
    public void updateTest(){
        // 게시물 수정 테스트
        Optional<Guestbook> result = guestbookRepository.findById(300L);
        // 300번이 해당되는 게시물을 찾음 -> 찾아온 게시물의 엔티티가 result에 들어감

        if(result.isPresent()) { // 객체가 있으면?
            Guestbook guestbook = result.get(); // 엔티티를 가져와서 변수에 넣음

            System.out.println(guestbook.getTitle());
            System.out.println(guestbook.getContent());
            System.out.println(guestbook.getWriter());
            // 내용 조회

            guestbook.changeTitle("수정된 제목");
            guestbook.changeContent("수정된 내용");
            guestbookRepository.save(guestbook); // 있으면 수정(update), 없으면 삽입(insert)
        }

    }

    @Test
    public void testQuery1(){   // 단일 조건으로 쿼리 생성 + 페이징 + 정렬
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());
        // 페이징 타입으로 요청을 처리함 -> 0번 페이지에 10개씩 객체 생성, gno를 기준으로 내림차순
        QGuestbook qGuestbook = QGuestbook.guestbook;
        // Querydsl용 객체 생성 (동적 처리)

        String keyword = "9";   // 프론트 페이지에서 1번을 찾겠다는 변수

        BooleanBuilder builder = new BooleanBuilder();  // 다중 조건 처리용 객체

        BooleanExpression expressionTitle = qGuestbook.title.contains(keyword);
        BooleanExpression expressionContent = qGuestbook.content.contains(keyword);

        BooleanExpression exAll = expressionTitle.or(expressionContent);    // 2개의 조건을 합체
        // expression (표현) >> title = 1 표현식이 생성됨

        builder.and(exAll);    // 다중 조건 처리용 객체에 표현식을 밀어넣음
        builder.and(qGuestbook.gno.gt(0L)); // while문 추가 (gno > 0)

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
        // 페이지 타입에 맞춰서 객체가 나옴 (findall은 검색한 모든 객체가 나옴)

        result.stream().forEach(guestbook -> {
            System.out.println("검색 결과 :" + guestbook);
        });

    }


}
