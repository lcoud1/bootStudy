package org.zerock.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // entitylistener를 동작 -> 엔티티 동작 감시용
public class BoardApplication {

    // 시작지점
    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class, args);
    }

}
