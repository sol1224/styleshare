package proj.petbuddy.repository.order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proj.petbuddy.domain.order.Orders;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query("select o from Orders o " +
            "where o.member.seq = :seq ")
    List<Orders> findOrders(@Param("seq") Long seq);

    @Query("select count(o) from Orders o " +
            "where o.member.seq = :seq")
    Long countOrder(@Param("seq") Long seq);

}
