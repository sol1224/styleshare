package proj.petbuddy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public class FooterController {

    /**
     * 회사소개
     **/
    @GetMapping("/brandIntro")
    public String brandIntro() {
        return "footerMenu/brandIntro";
    }

    /**
     * 입점문의
     **/
    @GetMapping("/launching")
    public String launching() {
        return "footerMenu/launching";
    }

    /**
     * 채용문의
     **/
    @GetMapping("/posting")
    public String posting() {
        return "footerMenu/posting";
    }

    /**
     * 채용문의
     **/
    @GetMapping("/personalInformation")
    public String personalInformation() {
        return "footerMenu/personalInformation";
    }

    /**
     * 소셜미디어
     **/
    @GetMapping("/sns")
    public String sns() {
        return "footerMenu/sns";
    }
}
