package proj.petbuddy.domain.board;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import proj.petbuddy.domain.category.Category;
import proj.petbuddy.domain.mypage.Member;
import proj.petbuddy.dto.board.BoardRequestDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class) // 엔티티의 생성/수정 시각 자동으로 기록
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "BOARD_ID")
    private Long id;

    // @JsonIgnore // 엔티티를 직접 노출할 때는 양방향 걸린 곳은 꼭 한곳을 @JsonIgnore 처리를 해야함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @CreatedDate
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updatedAt;

    private String writer;

    @Column(columnDefinition = "integer default 0")
    private Long viewCount; // 조회수

    @Column(columnDefinition = "integer default 0")
    private Long likeCount; // 좋아요 수

    private String delYN; // 삭제 여부
    private String password;
    private boolean open; // 공개 여부

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Category category;

    // 게시글 UI에서 댓글을 바로 보여주기 위해 FetchType을 EAGER로 설정
    // 게시글이 삭제되면 댓글 또한 삭제되어야 하기 때문에 CascadeType.REMOVE 속성을 사용
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id desc") // 댓글 정렬
//    @JsonBackReference //순환참조 방지
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Board(Member member, Long id, String title, String content) {
        this.title = title;
        this.content = content;
        this.member = member;
//        this.writer = writer;
//        this.viewCount = 0L;
//        this.likeCount = 0L;
//        this.delYN = "N";
//        this.open = true;


    }


    /**============ 비즈니스 로직 ============**/

    /**
     * DTO -> Entity
     * 게시글 작성
     */
    public Board(BoardRequestDTO requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.password = requestDto.getPassword();
        this.writer = requestDto.getWriter();
    }

    public static Board toEntity(BoardRequestDTO dto) {
        return Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }


    /**
     * 게시글 수정
     **/
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
