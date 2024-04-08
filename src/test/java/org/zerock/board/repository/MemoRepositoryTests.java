package org.zerock.board.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.zerock.board.entity.Memo;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass() {
        // 객체 주입 테스트 (memoRepository는 인터페이스임을 기억)
        // 인터페이스는 구현 객체가 있어야 한다.
        System.out.println(memoRepository.getClass().getName());
        // memoRepository에 생성된 객체의 클래스명과 이름을 알아보기위한 것
        // 콘솔 결과 : jdk.proxy3.$proxy115 (동적 프록시 : 인터페이스 실행 구현 클래스)
    }

    @Test
    public void testInsert() {
        // memo 테이블에 더미데이터 추가
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("샘플" + i).build();
            // Memo 클래스에 momoTest(1~100)생성 반복
            memoRepository.save(memo);  // .save(jpa 상속으로 사용)

        });

    }

    @Test
    public void testSelect(){
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);
        // import java.util.Optional;
        // select * from 표 where mno = 100;

        System.out.println("============100번 MNO==============");
        if (result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);   // 엔티티가 toString 되어 있음
        }

        // findById = 쿼리가 먼저 실행, 그 후에 결과 출력

    }

    @Transactional
    @Test
    public void testSelect2(){
        Long mno = 100L;
        Memo memo = memoRepository.getOne(mno);
        // getOne = 보안때문에 현재 차단되있는 메서드
        // @Transactional 필수, 변수가 호출될 때 쿼리가 그 후에 실행됨
        // no Session 오류 = Transactional 없어서
        // 정보가 있는지 확인하고 가져와야하기 때문에

        System.out.println("=============Long bno===============");
        System.out.println(memo);
    }
    
    @Test
    public void updateTests(){
        Memo memo = Memo.builder()
                .mno(100L)
                .memoText("수정된 텍스트")
                .build();

        System.out.println(memoRepository.save(memo));
        // save(memo) -> 없으면 insert, 이미 있다면 지 알아서 업데이트
    }

    @Test
    public void testDelete(){

        Long mno = 300L;
        memoRepository.deleteById(mno);

    }

    @Test
    public void testPageDefault() {
        // jpa에 내장된 페이징, 정렬 기법 활용

        Sort sort1 = Sort.by("mno").descending();       // 메모 번호 기준 내림차순
        Sort sort2 = Sort.by("memoText").ascending();   // 메모 텍스트 기준 오름차순
        Sort sortAll = sort1.and(sort2);    // 내림차순 번호 & 메모 텍스트 오름

        Pageable pageable = PageRequest.of(0, 10, sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);

        //Hibernate :
        // select m1_0.mno, m1_0.memo_text from tbl_memo m1_0
        // limit ?, ?
        //Hibernate:
        // select count(m1_0.mno) from tbl_memo m1_0
        //Page 1 of 10 containing org.zerock.boardboot.entity.Memo instances

        System.out.println("---------------------------------------");

        for(Memo memo : result.getContent()) {
            System.out.println(memo);
            //Memo(mno=1, memoText=Sample....1) ~ Memo(mno=10, memoText=Sample....10)
        }

    }

}