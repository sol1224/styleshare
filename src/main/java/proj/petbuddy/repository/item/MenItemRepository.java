package proj.petbuddy.repository.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proj.petbuddy.domain.item.MenItem;

@Repository
public interface MenItemRepository extends JpaRepository<MenItem, Long> {

    Page<MenItem> findAll(Pageable pageable);

//    /**
//     * 검색창 (남성_브랜드 or 상품명)
//     **/
//    @Query("SELECT m FROM MenItem m WHERE m.brand = :keyword OR m.name LIKE %:keyword%")
//    Page<MenItem> findByBrandOrName(@Param("keyword") String keyword, Pageable pageable);
}
