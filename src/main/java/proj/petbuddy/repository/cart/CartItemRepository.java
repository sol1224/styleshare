package proj.petbuddy.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proj.petbuddy.domain.cart.CartItem;
import proj.petbuddy.dto.cart.CartDetailDto;

import java.util.List;

/** 장바구니에 들어갈 상품을 저장하거나 조회 **/
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    @Query("select new proj.petbuddy.dto.cart.CartDetailDto(ci.id, i.name, i.price, ci.count, i.imgStr) " +
            "from CartItem ci " +
            "join ci.item i " +
            "on i.id = i.id " +
            "where ci.cart.id = :cartId")
    List<CartDetailDto> findCartDetailDtoList(@Param("cartId") Long cartId);
}

