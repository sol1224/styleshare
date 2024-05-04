package proj.petbuddy.domain.board;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import proj.petbuddy.domain.mypage.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Heart {

    @Id
    @GeneratedValue
    @JoinColumn(name = "HEART_ID")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @Builder
    public Heart(Long seq, Member member, Board board) {
        this.seq = seq;
        this.member = member;
        this.board = board;
    }
}
