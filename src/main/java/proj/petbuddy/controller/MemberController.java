package proj.petbuddy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import proj.petbuddy.domain.mypage.Member;
import proj.petbuddy.dto.member.MemberFormDto;
import proj.petbuddy.service.member.MemberService;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder PasswordEncoder;

    /**
     * 회원 가입
     */
    @GetMapping("/new")
    public String memberForm(@ModelAttribute MemberFormDto memberFormDto, Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "/mypage/join";
    }

    @PostMapping("/new")
    public String memberFromSave(@Validated MemberFormDto memberFormDto,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/mypage/join";
        }
        try {
            Member newMember = Member.CreateMember(memberFormDto, PasswordEncoder);
            memberService.saveMember(newMember);
            model.addAttribute("newMember", newMember);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/mypage/join";
        }

        return "mypage/joinSaved"; // 회원 가입 후 메인 화면으로 이동
    }

    /** 중복회원 검증 **/
    @PostMapping("/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam("id") String id) {

        int cnt = memberService.idCheck(id);
        return cnt;
    }


}
