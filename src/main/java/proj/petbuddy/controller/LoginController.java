package proj.petbuddy.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proj.petbuddy.domain.mypage.EventReceipt;
import proj.petbuddy.domain.mypage.Member;
import proj.petbuddy.domain.mypage.PersonalInformation;
import proj.petbuddy.dto.member.MemberRequestDTO;
import proj.petbuddy.repository.member.MemberRepository;
import proj.petbuddy.service.member.MemberService;


@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    /**
     * ENUM 사용
     **/
    @ModelAttribute("eventStatuses")
    public EventReceipt[] eventStatuses() {
        return EventReceipt.values();
    }

    @ModelAttribute("personalInformation")
    public PersonalInformation[] personalInformation() {
        return PersonalInformation.values();
    }

    /**
     * ----------------------------------------------------------------------------
     */

    @GetMapping("/login")
    public String loginForm(@AuthenticationPrincipal User user, Model model) {

//        if(user != null){
//            model.addAttribute("userName",user.getUsername());
//        }else {
//            new IllegalStateException("사용자가 존재하지 않습니다");
//        }
//
//        return "/mypage/login";
        if (user == null || user.getUsername() == null) {
            // 사용자가 로그인되어 있지 않거나 사용자 이름이 null인 경우 로그인 페이지로 리다이렉트
            return "mypage/login";
        } else {
            model.addAttribute("userName", user.getUsername());
            return "mypage/login";
        }

    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "mypage/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }

    /**
     * 마이페이지
     */

    @GetMapping("/mypage")
    public String mypage(Model model, @AuthenticationPrincipal User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            return "redirect:/login"; // 사용자가 로그인되어 있지 않으면 로그인 페이지로 리다이렉트
        }

        Member member = memberService.findMember(user.getUsername());
        log.info("member: " + member);

        if (member == null) {
            return "redirect:/login"; // 사용자 정보를 찾을 수 없는 경우에도 로그인 페이지로 리다이렉트
        }

        model.addAttribute("member", member);
        return "mypage/mypage";
    }

    /**
     * 마이페이지 수정
     */
    @PostMapping("/mypage")
    public String mypageUpdate(@RequestParam(value = "amount", defaultValue = "0") Long amount,
                               Model model,
                               @AuthenticationPrincipal User user,
                               MemberRequestDTO requestDTO) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            return "redirect:/login"; // 사용자가 로그인되어 있지 않으면 로그인 페이지로 리다이렉트
        }

        Member member = memberService.findMember(user.getUsername());

        if (member == null || !member.getId().equals(user.getUsername())) {
            return "redirect:/login"; // 사용자 정보를 찾을 수 없거나 일치하지 않는 경우에도 로그인 페이지로 리다이렉트
        }

        if (amount != null) {
            memberService.cashCharge(user.getUsername(), amount);
        }

        Member memberUpdate = memberService.updateMember(member.getId(), requestDTO);
        model.addAttribute("member", memberUpdate);

        return "redirect:/mypage";
    }

}

