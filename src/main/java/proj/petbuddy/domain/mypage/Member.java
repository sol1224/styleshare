package proj.petbuddy.domain.mypage;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import proj.petbuddy.domain.board.Board;
import proj.petbuddy.domain.board.Comment;
import proj.petbuddy.domain.order.Orders;
import proj.petbuddy.dto.member.MemberFormDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "member")
@NoArgsConstructor
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "MEMBER_ID")
    private Long seq;

    private String id;
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role; // 권한 [ROLE_USER, ROLE_ADMIN]

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    @JsonBackReference //순환참조 방지
    private List<Comment> comment;

    private Long money = 0L;
    private String password;
    private String passwordCheck;
    private String birth;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private EventReceipt eventReceipt;
    @Enumerated(EnumType.STRING)
    private PersonalInformation personalInformation;


//    @Column(name = "USER_ROLE")
//    @Enumerated(EnumType.STRING)
//    private Authority authority;

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Board> boards = new ArrayList<>();

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Orders> orders = new ArrayList<>();

    public void setMoney(Long money) {
        this.money = money;
    }

    public static Member CreateMember(MemberFormDto memberFormDto,
                                      PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setId(memberFormDto.getId());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setPassword(passwordEncoder.encode(memberFormDto.getPassword()));
        member.setPasswordCheck(member.getPasswordCheck());
        member.setBirth(member.getBirth());
        member.setPhoneNumber(member.getPhoneNumber());
        member.setRole(Role.USER); // 가입시 role은 무조건 회원으로 저장
        member.setEventReceipt(memberFormDto.getEventReceipt());
        member.setPersonalInformation(memberFormDto.getPersonalInformation());
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Builder
    public Member(String phoneNumber, String email, EventReceipt eventReceipt, PersonalInformation personalInformation) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.eventReceipt = eventReceipt;
        this.personalInformation = personalInformation;
        this.money = 0L;
//        this.viewCount = 0L;
//        this.likeCount = 0L;
//        this.delYN = "N";
//        this.open = true;
    }


    /**
     * 게시글 수정
     **/
    public void update(String phoneNumber, String email, EventReceipt eventReceipt, PersonalInformation personalInformation) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.eventReceipt = eventReceipt;
        this.personalInformation = personalInformation;
    }


}