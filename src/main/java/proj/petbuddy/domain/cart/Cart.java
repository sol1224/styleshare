package proj.petbuddy.domain.cart;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import proj.petbuddy.domain.board.BaseEntity;
import proj.petbuddy.domain.mypage.Member;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart extends BaseEntity {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long totalPrice;
    private Long DeliveryFee;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);
//        cart.setTotalPrice(totalPrice);
//        cart.setDeliveryFee(deliveryFee);
        return cart;
    }
}