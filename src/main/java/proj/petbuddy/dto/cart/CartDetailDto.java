package proj.petbuddy.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailDto {
    private Long cartItemId;
    private String name;
    private int price;
    private int count;
    private String imgStr;

    public CartDetailDto(Long cartItemId, String name, int price, int count, String imgStr) {
        this.cartItemId = cartItemId;
        this.name = name;
        this.price = price;
        this.count = count;
        this.imgStr = imgStr;
    }
}
