package proj.petbuddy.dto.item;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import proj.petbuddy.domain.item.Item;
import proj.petbuddy.domain.item.WomenItem;
import proj.petbuddy.domain.item.ItemImg;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemImgDTO {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private Item item;


    @Builder
    public ItemImgDTO(String imgName, String oriImgName, String imgUrl, String repImgYn) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
        this.repImgYn = repImgYn;
    }

    public ItemImg toEntity(ItemImgDTO dto) {
        ItemImg entity = ItemImg.builder()
                .imgName(dto.imgName)
                .oriImgName(dto.oriImgName)
                .imgStr(dto.imgUrl)
//                .repimgYn(dto.repImgYn)
                .build();

        return entity;
    }

    public ItemImgDTO of(ItemImg entity) {
        ItemImgDTO dto = ItemImgDTO.builder()
                .imgName(entity.getImgName())
                .oriImgName(entity.getOriImgName())
                .imgUrl(entity.getImgStr())
//                .repImgYn(entity.getRepimgYn())
                .build();

        return dto;
    }
}