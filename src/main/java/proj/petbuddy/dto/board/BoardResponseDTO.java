package proj.petbuddy.dto.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import proj.petbuddy.domain.board.Board;
import proj.petbuddy.domain.mypage.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BoardResponseDTO {

    private Long id;
    private String title;
    private String writer;
    private String content;
    private LocalDate createdAt, updatedAt;
    private Long view;
    //    private Long userSeq;
    private List<CommentDTO> comments;
    private Member member;


    /**
     * Entity -> DTO
     */
    public BoardResponseDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getWriter();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
        this.view = board.getViewCount();
        this.member = board.getMember();
        this.comments = board.getComments().stream().map(CommentDTO::new).collect(Collectors.toList());

    }


}
