package proj.petbuddy.domain.order;

import lombok.Getter;
import lombok.Setter;
import proj.petbuddy.domain.stutus.OrderStatus;

@Getter
@Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
