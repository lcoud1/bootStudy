package org.zerock.board.service;

import org.zerock.board.dto.GuestbookDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Guestbook;

public interface GuestbookService {
    Long register(GuestbookDTO dto);
    // 방명록을 등록하기 위함

    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    // GuestbookDTO를 이용해서 필요한 내용을 전달받음 + 반환도 그 쪽을 통함
    // 서비스 계층에서는 파라미터를 DTO 타입으로 받기때문에 jpa로 받기 위해서는 엔티티 타입으로 변환해서 받아야함

    default Guestbook dtoToEntity(GuestbookDTO dto){   // dto -> entity
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    default GuestbookDTO entityToDto(Guestbook entity){   // entity -> dto

        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }


}
