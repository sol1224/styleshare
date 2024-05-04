/**
 * 회원 가입 정상처리 테스트 확인
 */
package proj.petbuddy.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import proj.petbuddy.domain.mypage.Member;
import proj.petbuddy.dto.member.MemberFormDto;
import proj.petbuddy.service.member.MemberService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@gmail.com");
        memberFormDto.setName("홍길동");
//        memberFormDto.setAddress("부산시 행복동");
        memberFormDto.setPassword("1234");
        return  Member.CreateMember(memberFormDto,passwordEncoder);
    }
    @Test
    @DisplayName("회원가입 테스트")
    @Rollback(value = false)
    public void saveMemberTest(){
        Member member = createMember(); // 회원 테스트 데이터 생성
        Member savedmember = memberService.saveMember(member);

        // assertEquals (기댓값, 실제 저장된값)
        assertEquals(member.getEmail(),savedmember.getEmail());
        assertEquals(member.getName(),savedmember.getName());
//        assertEquals(member.getAddress(),savedmember.getAddress());
        assertEquals(member.getPassword(),savedmember.getPassword());
        assertEquals(member.getRole().toString(),savedmember.getRole().toString());
//        assertEquals(member.getGender(),savedmember.getGender());
    }


}