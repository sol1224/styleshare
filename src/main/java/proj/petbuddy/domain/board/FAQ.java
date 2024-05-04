package proj.petbuddy.domain.board;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FAQ {

    @Id
    @GeneratedValue
    @JoinColumn(name = "FAQ_ID")
    private Long seq;
    private String title;
    @Lob
    private String content;
    private Long viewCount;

}
