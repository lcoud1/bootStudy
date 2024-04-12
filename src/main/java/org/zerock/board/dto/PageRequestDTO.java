package org.zerock.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder    // 빌더 패턴
@AllArgsConstructor // 의존성 주입
@Data   // getter, setter 생성용
public class PageRequestDTO {   // 페이징 요청 처리 DTO
    
    private int page;   // 페이지
    private int size;   // 크기

    private String type; // 타입

    private String keyword; // 키워드
    
    public PageRequestDTO(){   // 페이징 요청치
        this.page = 1;
        this.size = 10;
    }
    
    public Pageable getPageable(Sort sort){
        return PageRequest.of(page -1, size, sort);
        // 페이지 번호, 페이지 크기 , 정렬 조건
    }
}

// 목록 처리 시 고려 사항 : 화면에서 필요한 목록 데이터에 대한 DTO 생성
//                       DTO를 Pageable 타입으로 전환
//                       Page<Entity>를 화면에서 사용하기 쉬운 DTO의 리스트 등으로 변환
//                       화면에 필요한 페이지 번호를 처리