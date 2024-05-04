package proj.petbuddy.domain.stutus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import proj.petbuddy.domain.order.Orders;
import proj.petbuddy.domain.mypage.Address;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Orders orders;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // Ready, Comp
}