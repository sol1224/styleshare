package proj.petbuddy.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import proj.petbuddy.domain.item.Item;
import proj.petbuddy.domain.item.ItemImg;

@NoArgsConstructor
@Getter
public class ItemDetailResponse {

    private Long id;
    private String brand;
    private String name;
    private Integer price;
    private Integer discount;
    private String imgStr;
    private Integer heart;
    private ItemImg itemImg;


    /**
     * Entity -> DTO
     */
    public ItemDetailResponse(Item item) {
        if (item != null) {
            this.id = item.getId();
            this.brand = item.getBrand();
            this.name = item.getName();
            this.price = item.getPrice();
            this.discount = item.getDiscount();
            this.imgStr = item.getImgStr();
            this.heart = item.getHeart();
            this.itemImg = item.getItemImg();
        } else {
        }
    }
}
