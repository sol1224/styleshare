package proj.petbuddy.service.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import proj.petbuddy.domain.cart.Cart;
import proj.petbuddy.domain.cart.CartItem;
import proj.petbuddy.domain.item.Item;
import proj.petbuddy.domain.mypage.Member;
import proj.petbuddy.dto.cart.CartDetailDto;
import proj.petbuddy.dto.cart.CartItemDto;
import proj.petbuddy.dto.cart.CartOrderDto;
import proj.petbuddy.dto.order.OrderDto;
import proj.petbuddy.repository.cart.CartItemRepository;
import proj.petbuddy.repository.cart.CartRepository;
import proj.petbuddy.repository.item.ItemRepository;
import proj.petbuddy.repository.member.MemberRepository;
import proj.petbuddy.service.order.OrderService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;

    public Long addCart(CartItemDto cartItemDto, String id){
        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(() -> new EmptyResultDataAccessException("Item not found", 1));

        Member member = memberRepository.findById(id);    // 현재 로그인한 회원 엔티티를 조회

        if (member == null) {
            throw new EmptyResultDataAccessException("Member not found", 1);
        }

        Cart cart = cartRepository.findByMemberSeq(member.getSeq());  // 현재 로그인한 회원의 장바구니 엔티티를 조회
        if(cart == null){   // 상품을 처음으로 장바구니에 담을 경우 해당 회원의 장바구니 엔티티를 생성
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());  // 현재 상품이 장바구니에 이미 들어가 있는지 조회
        if(savedCartItem != null){
            savedCartItem.addCount(cartItemDto.getCount()); // 장바구니 엔티티, 상품 엔티티, 장바구니에 담을 수량을 이용하여 CartItem 엔티티를 생성
            return savedCartItem.getId();
        } else {
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());    // 장바구니에 들어갈 상품을 저장
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String id){
        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findById(id);
        Cart cart = cartRepository.findByMemberSeq(member.getSeq());

        if(cart == null){
            return cartDetailDtoList;
        }
        cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());
        return cartDetailDtoList;
    }


    public void deleteCartItem(Long cartItemId) throws Exception{
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new Exception("Cart item not found"));
        cartItemRepository.delete(cartItem);
    }



//     장바구니에서 주문하기 위해 보낸 리스트들을 주문 폼 리스트로 만들어서 주문로직에 전달
    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, Long id) throws Exception{

        List<OrderDto> orderDtoList = new ArrayList<>();

        // 장바구니페이지에서 보낸 상품들을 하나씩 돌면서 주문 객체로 만들고,
        // 이 주문 객체들을 리스트로 만듦
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId())
                    .orElseThrow(() -> new Exception("Cart item not found"));
            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDto.setCount(cartItem.getCount());
            orderDtoList.add(orderDto);
        }

        // 주문 폼 리스트로 주문을 생성하고, 주문 번호를 반환
        Long orderId = orderService.orders(orderDtoList, id);

        // 주문한 상품들을 장바구니에서 삭제하는 작업
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId())
                    .orElseThrow(() -> new Exception("Cart item not found"));
            cartItemRepository.delete(cartItem);
        }
        return orderId;
    }

    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String id) throws Exception{
        Member curMemebr = memberRepository.findById(id);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception("Cart item not found"));

        Member savedMember = cartItem.getCart().getMember();

        if(!StringUtils.equals(curMemebr.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }

    // 장바구니 상품의 수량을 업데이트
    public void updateCartItemCount(Long cartItemId, int count) throws Exception{
        CartItem cartITem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception("Cart item not found"));

        cartITem.updateCount(count);
    }


}