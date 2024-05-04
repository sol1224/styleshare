package proj.petbuddy.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import proj.petbuddy.domain.mypage.Member;
import proj.petbuddy.dto.cart.CartDetailDto;
import proj.petbuddy.dto.cart.CartItemDto;
import proj.petbuddy.dto.cart.CartOrderDto;
import proj.petbuddy.dto.order.OrderHistDto;
import proj.petbuddy.dto.order.OrderItemDto;
import proj.petbuddy.repository.cart.CartItemRepository;
import proj.petbuddy.repository.order.OrderItemRepository;
import proj.petbuddy.repository.order.OrderRepository;
import proj.petbuddy.service.cart.CartService;
import proj.petbuddy.service.member.MemberService;
import proj.petbuddy.service.order.OrderService;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final MemberService memberService;
    private final OrderService orderService;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @GetMapping(value = "/cart")
    public String orderHist(Model model, @AuthenticationPrincipal User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            return "redirect:/login"; // 사용자가 로그인되어 있지 않으면 로그인 페이지로 리다이렉트
        }

        Member member = memberService.findMember(user.getUsername());
        List<CartDetailDto> cartDetailList = cartService.getCartList(member.getId());

        model.addAttribute("cartItems", cartDetailList);
        model.addAttribute("member", member);
        return "mypage/cart";
    }


    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order(@Valid CartItemDto cartItemDto, BindingResult bindingResult, @AuthenticationPrincipal User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다."); // 로그인되어 있지 않으면 UNAUTHORIZED 반환
        }
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        Member member = memberService.findMember(user.getUsername());
        String name = member.getId();
        Long cartItemId;


        try {
            cartItemId = cartService.addCart(cartItemDto, name);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @GetMapping(value = "/orders")
    public String orderComplete(Model model, @AuthenticationPrincipal User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            return "redirect:/login"; // 사용자가 로그인되어 있지 않으면 로그인 페이지로 리다이렉트
        }

        Member member = memberService.findMember(user.getUsername());
        List<OrderHistDto> orderList = orderService.getOrderList(member.getSeq());


        for (OrderHistDto orderHistDto : orderList) {
            System.out.println("Order ID: " + orderHistDto.getOrderId());
            System.out.println("Order Items:");
            List<OrderItemDto> orderItemDtoList = orderHistDto.getOrderItemDtoList();

            model.addAttribute("orderItemDtoList", orderItemDtoList);
            model.addAttribute("member", member);

            int totalPrice = 0;

            for (OrderItemDto orderItemDto : orderItemDtoList) {

                int i = orderItemDto.getCount() * orderItemDto.getOrderPrice();
                totalPrice += i;


                System.out.println("totalPrice:" + totalPrice);
                System.out.println("  - Item Name: " + orderItemDto.getItemNm());
                model.addAttribute("totalPrice", totalPrice);
            }
        }

        return "order/orderComplete";
    }


    @PostMapping(value = "/orders")
    public String order_(Model model, @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            return "redirect:/login"; // 사용자가 로그인되어 있지 않으면 로그인 페이지로 리다이렉트
        }

        Member member = memberService.findMember(user.getUsername());
        List<OrderHistDto> orderList = orderService.getOrderList(member.getSeq());

        int totalPrice = 0;

        for (OrderHistDto orderHistDto : orderList) {
            List<OrderItemDto> orderItemDtoList = orderHistDto.getOrderItemDtoList();

            for (OrderItemDto orderItemDto : orderItemDtoList) {
                int i = orderItemDto.getCount() * orderItemDto.getOrderPrice();
                totalPrice += i;
            }
        }

        long memberMoney = member.getMoney();

        model.addAttribute("memberMoney", memberMoney);
        model.addAttribute("totalPrice", totalPrice);

        return "order/orderComplete";
    }


    @PostMapping(value = "/orders/orderComplete")
    public String orderComplete_(Model model, @AuthenticationPrincipal User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            return "redirect:/login"; // 사용자가 로그인되어 있지 않으면 로그인 페이지로 리다이렉트
        }

        Member member = memberService.findMember(user.getUsername());
        List<OrderHistDto> orderList = orderService.getOrderList(member.getSeq());


        for (OrderHistDto orderHistDto : orderList) {
            System.out.println("Order ID: " + orderHistDto.getOrderId());
            System.out.println("Order Items:");
            List<OrderItemDto> orderItemDtoList = orderHistDto.getOrderItemDtoList();

            model.addAttribute("orderItemDtoList", orderItemDtoList);
            model.addAttribute("member", member);

            int totalPrice = 0;

            for (OrderItemDto orderItemDto : orderItemDtoList) {

                int i = orderItemDto.getCount() * orderItemDto.getOrderPrice();
                totalPrice += i;


                System.out.println("totalPrice:" + totalPrice);
                System.out.println("  - Item Name: " + orderItemDto.getItemNm());
                model.addAttribute("totalPrice", totalPrice);
            }
        }
        return "redirect:/orders/orderComplete";
    }


    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity orderCartItem(@RequestBody CartOrderDto cartOrderDto, @AuthenticationPrincipal User user, Model model) throws Exception {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다."); // 로그인되어 있지 않으면 UNAUTHORIZED 반환
        }

        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if (cartOrderDtoList == null || cartOrderDtoList.size() == 0) {
            return new ResponseEntity<String>("주문할 상품을 선택해주세요", HttpStatus.FORBIDDEN);
        }

        for (CartOrderDto cartOrder : cartOrderDtoList) {
            if (!cartService.validateCartItem(cartOrder.getCartItemId(), user.getUsername())) {
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }

        }

        /** 포인트 사용 후 변경된 money 세팅
         * cart.html 에서 ajax 로 값을 받음
         * paramData['calculationOfAmount'] = calculationOfAmount; 참고
         * */
        // 전달된 값 중에서 calculationOfAmount 값을 가져오기

        long calculationOfAmount = cartOrderDto.getCalculationOfAmount();
        System.out.println("calculationOfAmount" + calculationOfAmount);

        Member member1 = memberService.findMember(user.getUsername());
        member1.setMoney(calculationOfAmount);
        Long orderId = cartService.orderCartItem(cartOrderDtoList, member1.getSeq());


        System.out.println(" cartOrderDto.getCounterTotalAmt()" + cartOrderDto.getCalculationOfAmount());
        System.out.println(" cartOrderDto.getCounterTotalAmt()" + cartOrderDto.getCounterTotalAmt());


        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }


//    @PatchMapping(value = "/cartItem/{cartItemId}")
//    public @ResponseBody ResponseEntity updateCartItem(@PathVariable("cartItemId") Long cartItemId, int count, Principal principal) throws Exception {
//        if (count < 0) {
//            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
//        } else if (!cartService.validateCartItem(cartItemId, principal.getName())) {
//            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.BAD_REQUEST);
//        }
//
//        System.out.println("count: " + count);
//
//        cartService.updateCartItemCount(cartItemId, count);
//        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
//    }
//
//
//    @DeleteMapping(value = "/cartItem/{cartItemId}")
//    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal) throws Exception {
//        if (!cartService.validateCartItem(cartItemId, principal.getName())) {
//            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.BAD_REQUEST);
//        }
//        cartService.deleteCartItem(cartItemId);
//        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
//    }


}