package proj.petbuddy.dto.order;

import lombok.Getter;
import lombok.Setter;
import proj.petbuddy.domain.order.Orders;
import proj.petbuddy.domain.stutus.OrderStatus;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {

    public OrderHistDto(Orders orders){
        this.orderId = orders.getId();
        this.orderDate = orders.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//        this.orderStatus = orders.getOrderStatus();
    }

    private Long orderId; //주문아이디
    private String orderDate; //주문날짜
    private OrderStatus orderStatus; //주문 상태
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>(); //주문 상품리스트


    public Long getOrderId() {
        return this.orderId;
    }


    public void addOrderItemDto(OrderItemDto orderItemDto){
        orderItemDtoList.add(orderItemDto);
    }



}
