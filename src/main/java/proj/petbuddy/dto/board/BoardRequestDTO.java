/**
 * 작성자 부분 수정필요 (로그인구현)
 */
package proj.petbuddy.dto.board;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import proj.petbuddy.domain.board.Board;
import proj.petbuddy.domain.mypage.Member;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BoardRequestDTO {

    private Long id;
    @NotBlank(message = "제목을 입력해주세요.")
    @Column(nullable = false, length = 100)
    private String title;

    private Member member;

    @NotBlank(message = "내용을 입력해주세요.")
    @Column(nullable = false, length = 10000)
    private String content;

    private String writer;
    private String password;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;


    /**
     * Dto -> Entity
     */
    public Board toEntity() {
        Board boards = Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .member(member)
                .writer(writer)
                .build();

        return boards;
    }
}