package proj.petbuddy.dto.order;

import lombok.Getter;
import lombok.Setter;
import proj.petbuddy.domain.order.OrderItem;

@Getter
@Setter
public class OrderItemDto {

    private Long orderId;
    private String itemNm; //상품명
    private int count; //주문 수량

    private int orderPrice; //주문 금액
    private String imgStr; //상품 이미지 경로

    public OrderItemDto(OrderItem orderItem) {
        this.orderId = orderItem.getOrders().getId();
        this.itemNm = orderItem.getItem().getName();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgStr = orderItem.getItem().getImgStr();
    }
}