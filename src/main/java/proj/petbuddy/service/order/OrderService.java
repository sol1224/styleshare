package proj.petbuddy.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.petbuddy.domain.item.Item;
import proj.petbuddy.domain.item.ItemImg;
import proj.petbuddy.domain.mypage.Member;
import proj.petbuddy.domain.order.OrderItem;
import proj.petbuddy.domain.order.Orders;
import proj.petbuddy.dto.order.OrderDto;
import proj.petbuddy.dto.order.OrderHistDto;
import proj.petbuddy.dto.order.OrderItemDto;
import proj.petbuddy.repository.item.ItemImgRepository;
import proj.petbuddy.repository.item.ItemRepository;
import proj.petbuddy.repository.member.MemberRepository;
import proj.petbuddy.repository.order.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;

    /**
     * 주문
     * 장바구니에서 주문할 상품 데이터를 전달받아 주문을 생성하는 로직
     */
   /*
    장바구니에서 주문할 상품 데이터를 전달받아 주문을 생성하는 로직
     */
    public Long orders(List<OrderDto> orderDtoList, Long id) throws Exception{

        Member member = memberRepository.findBySeq(id);

        List<OrderItem> orderItemList = new ArrayList<>(); // 1. 주문상품 리스트

        for(OrderDto orderDto : orderDtoList){ // 주문 폼 돌면서 각 상품으로 주문상품 만들어서 넣음
            Item item = itemRepository.findById(orderDto.getItemId())
                    .orElseThrow(() -> new Exception("Cart item not found"));
            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            orderItemList.add(orderItem);
        }

        Orders orders = Orders.createOrder(member, orderItemList); // 주문 만들어서 주문 상품 리스트 넣음
        orderRepository.save(orders);

        return orders.getId();
    }

    /*

1. orderItemList를 만든다.
2. orderItem을 만들어서 List에 추가
3. Order를 만들고 해당 List를 추가
4. Order의 id 반환

 */
    public Long order(OrderDto orderDto, String id) throws Exception{
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(() -> new Exception("Cart item not found"));
        Member member = memberRepository.findById(id);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        Orders orders = Orders.createOrder(member, orderItemList);
        orderRepository.save(orders);

        return orders.getId();

    }

    /**
     * 주문 취소
     */
//    @Transactional
//    public void cancelOrder(String orderId){
//        //주문 엔티티 조회
//        orderRepository.findOne(orderId);
//        //주문 취소
//        order.cancel();
//    }

    @Transactional(readOnly = true)
    public List<OrderHistDto> getOrderList(Long seq) {

        List<Orders> orders = orderRepository.findOrders(seq);
        Long totalCount = orderRepository.countOrder(seq);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for (Orders order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                ItemImg itemImg = itemImgRepository.findByItemId(orderItem.getItem().getId());
                OrderItemDto orderItemDto = new OrderItemDto(orderItem);
                orderHistDto.addOrderItemDto(orderItemDto);
            }

            orderHistDtos.add(orderHistDto);
        }

        return orderHistDtos;
    }




}
