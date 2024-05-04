package proj.petbuddy.domain.order;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import proj.petbuddy.domain.mypage.Member;
import proj.petbuddy.domain.stutus.Delivery;
import proj.petbuddy.domain.stutus.DeliveryStatus;
import proj.petbuddy.domain.stutus.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "ORDERS")
public class Orders {

    @Id
    @GeneratedValue
    @Column(name = "orders_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER , CANCEL]

//    private int orderPrice;

    // 연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrders(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrders(this);
    }

//    public void setOrderPrice(int orderPrice) {
//        this.orderPrice = orderPrice;
//    }

    // 생성 메서드
    public static Orders createOrder(Member member, List<OrderItem> orderItems) {
        Orders orders = new Orders();
        orders.setMember(member);
//        orders.setDelivery(delivery);

        for (OrderItem orderItem : orderItems) {
            orders.addOrderItem(orderItem);
        }
        orders.setStatus(OrderStatus.ORDER);
        orders.setOrderDate(LocalDateTime.now());
        return orders;
    }

    // 비즈니스 로직

    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // 조회 로직

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice+=orderItem.getTotalPrice();

        }
        return totalPrice;
    }
    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", memberId=" + getMember().getId() +
                ", orderDate=" + orderDate +
                ", status=" + status +
                // Add other fields as needed...
                '}';
    }

}