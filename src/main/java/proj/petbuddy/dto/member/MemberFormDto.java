package proj.petbuddy.dto.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import proj.petbuddy.domain.mypage.Address;
import proj.petbuddy.domain.mypage.EventReceipt;
import proj.petbuddy.domain.mypage.Member;
import proj.petbuddy.domain.board.Authority;
import proj.petbuddy.domain.board.Board;
import proj.petbuddy.domain.mypage.PersonalInformation;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MemberFormDto {
    private Long seq;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-z0-9])[a-z0-9]{6,20}$", message = "아이디 입력(6~20자)")
    @Column(unique = true)
    private String id;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$", message = "비밀번호 입력(문자, 숫자, 특수문자 포함 8~20자)")
    private String password;
    @NotBlank(message = "비밀번호 재입력")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$", message = "비밀번호가 일치하지 않습니다")
    private String passwordCheck;
    private String birth;
    @NotBlank(message = "핸드폰번호를 입력해주세요.")
    @Pattern(regexp = "^[0-9]*$", message = "핸드폰번호는 숫자만 입력 가능합니다.")
    private String phoneNumber;


    @Column(name = "USER_ROLE")
    @Enumerated(EnumType.STRING)
    private Authority authority; // 권한 [ROLE_USER, ROLE_ADMIN]

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")

    @Embedded
    private EventReceipt eventReceipt;
    @Embedded
    private PersonalInformation personalInformation;


    public static MemberFormDto toCommentDTO(Member member) {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setId(member.getId());
        memberFormDto.setSeq(member.getSeq());
        memberFormDto.setName(member.getName());
        memberFormDto.setEmail(member.getEmail());
        memberFormDto.setName(member.getName());
        memberFormDto.setBirth(member.getBirth());
        memberFormDto.setPassword(member.getPassword());
        memberFormDto.setPhoneNumber(member.getPhoneNumber());
//        memberFormDto.setAddress(member.getAddress());

        // Address 객체 생성 및 값 설정
        Address address = new Address();
        address.setAddress(member.getAddress().getAddress());
        address.setDetailAddress(member.getAddress().getDetailAddress());
        memberFormDto.setAddress(address);

        memberFormDto.setEventReceipt(member.getEventReceipt());
        memberFormDto.setPersonalInformation(member.getPersonalInformation());
        return memberFormDto;
    }
}

