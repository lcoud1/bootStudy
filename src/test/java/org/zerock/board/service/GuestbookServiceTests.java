package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.GuestbookDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Guestbook;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister(){

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("제목 예시")
                .content("내용 예시")
                .writer("사용자0")
                .build();
        System.out.println(service.register(guestbookDTO));
    }

    @Test
    public void testList(){

        // 페이지 요청 : 1페이지 당 글 개수는 10개
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        // 방명록 리스트 요청해서 가져오기
        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println("이전 :" + resultDTO.isPrev());
        System.out.println("다음 :" + resultDTO.isNext());
        System.out.println("전체 :" + resultDTO.getTotalPage());

        // 방명록 리스트 for문 돌려서 각 list들 조회
        System.out.println("-----------------------------------------");
        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }
        System.out.println("==========================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

    @Test
    public void testSearch(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("한글")
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println("이전" + resultDTO.isPrev());
        System.out.println("다음" + resultDTO.isNext());
        System.out.println("전체" + resultDTO.getTotalPage());

        System.out.println("--------------------------------------");
        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }

        System.out.println("----------------------------------------");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }



}
