package org.zerock.board.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data // getter, setter, toString 기타 등등 다해줌
@Builder(toBuilder = true)  // builder 패턴
public class SampleDTO {
    // DTO는 프론트에서 자바까지 객체를 담당함.
    // entity는 DB에서 자바까지 영속성을 담당함.
    // 나중에는 dtoToEntity, entityToDTO라는 메서드가 이 2개를 전이시키는 역할을 진행함
    // 자료와 정보는 확실히 구별해야함 다름
    // 얘네들은 DTO이기 때문에 DB까지 안감
    
    private Long sno;

    private String first;

    private String last;

    private LocalDateTime regTime;
    
  
}
