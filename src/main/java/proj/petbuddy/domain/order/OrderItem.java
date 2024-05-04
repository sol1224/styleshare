package proj.petbuddy.domain.order;

import jakarta.persistence.*;
import lombok.Data;
import proj.petbuddy.domain.item.Item;

@Entity
@Table(name = "order_item")
@Data
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    private int orderPrice;
    private int totalPrice;
    private int count;
    private String imgStr;

    // 생성 메서드
    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();

        orderItem.setItem(item);
        orderItem.setOrderPrice(item.getPrice());
        orderItem.setCount(count);
        orderItem.setImgStr(item.getImgStr());
        orderItem.setTotalPrice(item.getPrice() * count);
        item.removeStock(count);
        return orderItem;
    }

    // 비즈니스 로직

    /**
     * 주문 취소
     */
    public void cancel() {
        getItem().addCount(count);
    }

    // 조회 로직

    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice(){
        return orderPrice*count;
    }
}
