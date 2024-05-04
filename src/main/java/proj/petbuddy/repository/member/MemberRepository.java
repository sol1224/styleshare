package proj.petbuddy.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proj.petbuddy.domain.mypage.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findBySeq(Long seq);

    /** Security **/
    Optional<Member> findByName(String name);

    /** member GET **/
    Member findById(String name);

    /** 중복인 경우 true, 중복되지 않은 경우 false 리턴 **/
    boolean existsById(String id);

}
