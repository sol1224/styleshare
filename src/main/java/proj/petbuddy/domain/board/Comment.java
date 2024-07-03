package proj.petbuddy.domain.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import proj.petbuddy.domain.mypage.Member;
import proj.petbuddy.dto.board.CommentDTO;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne // board : comment 관계는 1:M
    @JsonManagedReference // 순환참조 방지
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    /**
     * DTO -> Entity
     **/
    public static Comment toSaveEntity(CommentDTO requestDTO, Board board) {
        Comment commentEntity = new Comment();
        commentEntity.setId(requestDTO.getId());
        commentEntity.setContent(requestDTO.getContent());
        commentEntity.setBoard(board);
        commentEntity.setMember(requestDTO.getMember());
        return commentEntity;
    }

}


