package proj.petbuddy.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.OrderBy;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import proj.petbuddy.domain.mypage.Member;
import proj.petbuddy.domain.board.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CommentDTO {

    private Long id;
    private String content;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt = LocalDateTime.now();
    @LastModifiedDate
    private LocalDateTime updatedAt = LocalDateTime.now();

    private Long boardId;

    @OrderBy("id desc") // 댓글 정렬
    private List<Comment> commentList;

    private String nickname;

    private Member member;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.boardId = comment.getBoard().getId();
        this.commentList = comment.getBoard().getComments();
        this.nickname = comment.getMember().getId();
        this.member = comment.getMember();
    }

    public static CommentDTO toCommentDTO(Comment comment, Long boardId) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setBoardId(boardId);
        commentDTO.setMember(comment.getMember());
        commentDTO.setCommentList(comment.getBoard().getComments());
        commentDTO.setMember(comment.getMember());
        commentDTO.setNickname(comment.getMember().getId());
        return commentDTO;
    }
}


