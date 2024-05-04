package proj.petbuddy.domain.item;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Table(name = "item_img")
@Entity
public class ItemImg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ITEM_IMG_ID")
    private Long id;

    private String imgName; //이미지 파일명

    private String oriImgName; //원본 이미지 파일명

    private String imgStr; //이미지 조회 경로

//    private String repimgYn; //대표 이미지 여부

    private String imgPath;


    @ManyToOne(fetch = FetchType.LAZY) // 상품 엔티티와 다대일 단방향 매핑
    @JoinColumn(name = "ITEM_ID")
    private Item item;


    @Builder
    public ItemImg(String imgName, String oriImgName, String imgStr, Item item) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgStr = imgStr;
//        this.repimgYn = repimgYn;
        this.item = item;
    }

    // 원본 이미지 파일명, 업데이트할 이미지 파일명, 이미지 경로를 파라미터로 입력 받아 이미지 정보를 업데이트하는 메소드
    public void updateItemImg(String oriImgName, String imgName, String imgStr) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgStr = imgStr;
    }
}
