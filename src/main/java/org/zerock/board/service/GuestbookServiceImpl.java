package org.zerock.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.board.dto.GuestbookDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Guestbook;
import org.zerock.board.repository.GuestbookRepository;

import java.util.function.Function;

@Service    // 빈으로 처리되도록 service 추가
@Log4j2     // 로그볼려고 추가
@RequiredArgsConstructor // 의존성 자동 주입
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository;
    // 저장소이기때문에 변환하면 안되서? + 의존성 주입

    @Override
    public Long register(GuestbookDTO dto){ // 등록

        log.info("DTO :");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto); // dto -> 엔티티로 변환

        log.info(entity);

        repository.save(entity);    // 변환된 엔티티를 저장소에 저장

        return entity.getGno();     // 저장된 엔티티의 gno
    }
    
    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO){

        // PageRequestDTO의 페이지 요청으로부터 페이지네이션을 위한 pageable 객체 생성
        // gno를 기준으로 내림차순
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        // 페이지네이션 정보를 바탕으로 방명록 리스트를 db에 조회
        Page<Guestbook> result = repository.findAll(pageable);

        // 엔티티 -> dto로 변환하는 함수 정의
        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));
        
        // 결과값 : dto 객체를 생성하여 반환
        return new PageResultDTO<>(result, fn);
    }

}
