package proj.petbuddy.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.petbuddy.domain.cart.Cart;

/** 현재 로그인한 회원의 Cart 엔티티를 찾기위한 인터페이스 **/
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByMemberSeq(Long id);
}
