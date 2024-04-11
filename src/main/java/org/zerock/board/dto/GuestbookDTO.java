package org.zerock.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GuestbookDTO {

    // 서비스 계층이 이용할 DTO

    private Long gno;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate, modDate;
}
