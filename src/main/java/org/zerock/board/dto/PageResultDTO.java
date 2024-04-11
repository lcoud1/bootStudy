package org.zerock.board.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {

    // DTO 리스트
    private List<DTO> dtoList;

    // 총 페이지 번호
    private int totalPage;

    // 현재 페이지 번호
    private int page;

    // 목록 사이즈
    private int size;

    // 시작, 끝 페이지 번호
    private int start, end;

    // 이전, 다음
    private boolean prev, next;

    // 페이지 번호 목록
    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){
        // EN -> entity
        // 페이지 결과를 나타냄 / 엔티티를 DTO로 변환하기 위한 함수
        // 주어진 페이지 결과를 DTO로 변환하고 리스트로 수집하여 DTO 리스트를 생성

        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());

    }

    private void makePageList(Pageable pageable) {

        this.page = pageable.getPageNumber() + 1; // 0부터 시작하므로 +1 해줌
        this.size = pageable.getPageSize(); //페이지 당 표시할 항목 수

        int tempEnd = (int)(Math.ceil(page/10.0)) * 10; // 현재 페이지를 기준으로 10개를 한 묶음
        start = tempEnd - 9;

        prev = start > 1;   // 이전 페이지가 있는지 확인

        // 현재 페이지 그룹의 끝 페이지 번호를 설정 /
        // 그룹의 끝 페이지 번호는 전체 페이지 수(totalPage)보다 크면 그 값을 사용하고,
        // 그렇지 않으면 전체 페이지 수를 사용합니다
        end = totalPage > tempEnd ? tempEnd: totalPage;

        // 다음 페이지 있는지 확인
        next = totalPage > tempEnd;

        // 페이지 목록 생성 / 현재 페이지 시작부터 끝까지의 페이지 번호를 리스트로 생성
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
