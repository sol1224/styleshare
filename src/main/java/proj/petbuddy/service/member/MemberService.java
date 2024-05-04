package proj.petbuddy.service.member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import proj.petbuddy.domain.mypage.Member;
import proj.petbuddy.dto.member.MemberRequestDTO;
import proj.petbuddy.repository.member.MemberRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * 회원 저장
     */
    @Transactional
    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);

    }

    /**
     * 회원 검색
     */
    @Transactional
    public Member findMember(String id) {
        return memberRepository.findById(id);
    }

    /**
     * 회원정보 수정
     */
    @Transactional
    public Member updateMember(String id, MemberRequestDTO requestDTO) {
        Member member = memberRepository.findById(id);
        member.update(requestDTO.getPhoneNumber(), requestDTO.getEmail(), requestDTO.getEventReceipt(), requestDTO.getPersonalInformation());
        return memberRepository.save(member);
    }

    /**
     * 중복회원 확인
     */
    @Transactional
    public void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findBySeq(member.getSeq());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
            // IllegalStateException : 메세지가 허가되지 않거나 부적절한 argment를 받았을때 예외를 발생
        }
    }
    /**
     * 중복회원 확인
     */
    @Transactional
    public int idCheck(String id) {
        Member existId = memberRepository.findById(id);
        if(existId!=null) {
            return 1;
        }else {
            return 0;
        }
    }


    /** 캐쉬 충전 **/
    @Transactional
    public Member cashCharge(String user, Long amount) {
        Member findUser = memberRepository.findById(user);
        if(findUser != null) {
            Long money = findUser.getMoney();
            Long totalMoney = money + amount;

            System.out.println("money:" + money);
            System.out.println("amount:" + amount);

            findUser.setMoney(totalMoney);
            return findUser;
        }else {
            throw new IllegalStateException("충전을 할 수 없습니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(id);
        if (member == null) {
            throw new UsernameNotFoundException(id);
        }

        // user 객체를 반환
        return User.builder()
                .username(member.getId())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }


}