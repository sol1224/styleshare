package proj.petbuddy.dto.item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import proj.petbuddy.domain.item.Item;
import proj.petbuddy.domain.item.ItemImg;

@Getter
@NoArgsConstructor
public class ItemResponseDTO {

    private Long id;
    private String oriImgName; //원본 이미지 파일명
    private Item item;
    private String imgStr;

    /**
     * Entity -> DTO
     */
    @Builder
    public ItemResponseDTO(ItemImg item) {
        this.id = item.getId();
        this.oriImgName = item.getOriImgName();
        this.item = item.getItem();
        this.imgStr = item.getImgStr();
    }


}
