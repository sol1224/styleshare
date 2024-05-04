package proj.petbuddy.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

// 장바구니 페이지에서 주문할 상품 데이터를 전달
@Getter
@Setter
public class CartOrderDto {

    private Long cartItemId;
    private int calculationOfAmount;
    private int counterTotalAmt;

    // 장바구니에서 여러 개의 상품을 주문하기 때문에 자기를 List로 가짐
    private List<CartOrderDto> cartOrderDtoList;

    // 생성자, getter 및 setter
    public CartOrderDto() {
    }

    public CartOrderDto(int counterTotalAmt) {
        this.counterTotalAmt = counterTotalAmt;
    }

    public CartOrderDto(Long cartItemId, int calculationOfAmount) {
        this.cartItemId = cartItemId;
        this.calculationOfAmount = calculationOfAmount;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCalculationOfAmount() {
        return calculationOfAmount;
    }

    public void setCalculationOfAmount(int calculationOfAmount) {
        this.calculationOfAmount = calculationOfAmount;
    }
}
