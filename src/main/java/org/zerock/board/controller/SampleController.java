package org.zerock.board.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.SampleDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@RestController     // 기본 값 = JSONs
@Controller
@RequestMapping("/sample")  // http://localhost/sample/??
@Log4j2
public class SampleController {

    @GetMapping("/hello")
    public String[] hello(){
        return new String[]{"Hello", "World"};
    }


    @GetMapping("/ex1") // http://localhost/sample/ex1.html -> void는 같은 경로와 파일명을 찾음
    public void ex1(){
        log.info("ex1 메서드 실행");
        // resources/templetes/sample/ex1.html
    }

    @GetMapping({"/ex2","/exlink"}) // http://localhost/sample/ex2.html
    public void exModel(Model model){
        // Spring은 model 타입으로 모든 객체나 데이터를 가지고있다.
        List<SampleDTO> list = IntStream.rangeClosed(1,20).asLongStream().mapToObj(i->{
            SampleDTO dto = SampleDTO.builder()
                    .sno(i)
                    .first("첫 번째 필드" + i)
                    .last("마지막 필드" + i)
                    .regTime(LocalDateTime.now())
                    .build();
                 return dto;

        }).collect(Collectors.toList());    // 리스트 완성
        /*model.addAllAttributes();   // 여러 객체용 */
        model.addAttribute("list",list);   // 단일 객체용
        // 프론트에서 list를 호출하면 list객체가 나옴
        // list -> return dto -> model로 담아야함
    }

    @GetMapping({"/exInline"})
    public String exInline(RedirectAttributes redirectAttributes){
        log.info("인라인 기능");

        SampleDTO dto = SampleDTO.builder()
                .sno(100L)
                .first("First")
                .last("Last")
                .regTime(LocalDateTime.now())
                .build();

                redirectAttributes.addFlashAttribute("result", "success");
                redirectAttributes.addFlashAttribute("dto", dto);

                return "redirect:/sample/ex3";
    }

    @GetMapping("/ex3")
    public void ex3(){
        log.info("ex3");
    }

    @GetMapping({"/exLayout1", "/exLayout2", "/exTemplete", "/exSidebar"})
    public void exLayout1(){
        log.info("exLayout");
    }


}
