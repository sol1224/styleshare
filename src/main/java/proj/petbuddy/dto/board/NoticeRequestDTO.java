package proj.petbuddy.dto.board;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import proj.petbuddy.domain.board.FAQ;

@Getter
@NoArgsConstructor
public class NoticeRequestDTO {

    private Long seq;

    @NotBlank(message = "제목을 입력해주세요.")
    @Column(nullable = false, length = 100)
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Column(nullable = false, length = 10000)
    private String content;

    private Long viewCount;

    @Builder
    public NoticeRequestDTO(FAQ faq) {
        this.title = faq.getTitle();
        this.content = faq.getContent();
    }
}
