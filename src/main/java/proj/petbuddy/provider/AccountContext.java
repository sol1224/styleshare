package proj.petbuddy.provider;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import proj.petbuddy.domain.mypage.Member;

import java.util.Collection;

public class AccountContext extends User {
    private final Member member; //나중에 참조할 수 있도록 전역변수 선언.

    public AccountContext(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getId(), member.getPassword(), authorities);
        this.member = member;
    }

    public Member getAccount() {
        return member;
    }
}
