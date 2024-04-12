package org.zerock.board.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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
import org.zerock.board.entity.QGuestbook;
import org.zerock.board.repository.GuestbookRepository;

import java.util.Optional;
import java.util.function.Function;

@Service    // 빈으로 처리되도록 service 추가
@Log4j2     // 로그볼려고 추가
@RequiredArgsConstructor // 의존성 자동 주입
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository repository;
    // 저장소이기때문에 변환하면 안되서? + 의존성 주입

    @Override
    public Long register(GuestbookDTO dto) { // 등록

        log.info("DTO :");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto); // dto -> 엔티티로 변환

        log.info(entity);

        repository.save(entity);    // 변환된 엔티티를 저장소에 저장

        return entity.getGno();     // 저장된 엔티티의 gno
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        // PageRequestDTO의 페이지 요청으로부터 페이지네이션을 위한 pageable 객체 생성
        // gno를 기준으로 내림차순
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        // 검색 조건 처리
        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        // 페이지네이션 정보를 바탕으로 방명록 리스트를 db에 조회
        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);

        // 엔티티 -> dto로 변환하는 함수 정의
        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        // 결과값 : dto 객체를 생성하여 반환
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {
        // gno에 해당하는 방명록을 조회
        Optional<Guestbook> result = repository.findById(gno);

        // 결과가 존재하는지 확인, 존재하면 entity -> dto로 변환하여 반환 / 존재하지 않으면 null값 반환
        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {

        // 업데이트 하는 항목은 '제목', '내용'
        Optional<Guestbook> result = repository.findById(dto.getGno());

        if (result.isPresent()) {

            Guestbook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }

    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        // querydsl 사용하여 동적 쿼리 처리 메서드

        // dto에서 키워드, 타입 가져오기
        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();

        // 빈 깡통 생성
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // querydsl의 q 클래스 가져오기
        QGuestbook qGuestbook = QGuestbook.guestbook;

        // 초기 검색 조건 설정 -> 게시글 번호
        BooleanExpression expression = qGuestbook.gno.gt(0L);
        booleanBuilder.and(expression);

        // 검색 조건이 null 값이거나, 비어 있는 경우 반환
        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        // 추가 검색 조건 생성
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        // type에 따라 조건 추가 -> t : title / c: content / w: writer
        if (type.contains("t")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }

        // 최종 조건 생성
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;

    }
}
