package org.zerock.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.service.GuestbookService;

@Controller // 컨트롤러 역할 수행
@RequestMapping("/guestbook")   // http://localhost/guestbook/*
@Log4j2
@RequiredArgsConstructor    // 자동 주입을 위한 annotation
public class GuestbookController {

    private final GuestbookService service; // final로 선언

    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("리스트 : "+ pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));

    }
}
