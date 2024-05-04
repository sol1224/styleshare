package proj.petbuddy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proj.petbuddy.domain.item.Item;
import proj.petbuddy.domain.map.Store;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAll();

    /**
     * 검색창 (남성_브랜드 or 상품명)
     **/
    @Query("SELECT s FROM Store s WHERE s.name = :keyword OR s.address LIKE %:keyword%")
    Store findByNameOrAddress(@Param("keyword") String keyword);


}
