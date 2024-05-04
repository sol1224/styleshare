package proj.petbuddy.repository.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proj.petbuddy.domain.item.WomenItem;

@Repository
public interface WomenItemRepository extends JpaRepository<WomenItem, Long> {

    Page<WomenItem> findAll(Pageable pageable);

    WomenItem findBySeq(Long seq);

    /**
     * 검색창 (여성_브랜드 or 상품명)
     **/
    @Query("SELECT w FROM WomenItem w WHERE w.brand = :keyword OR w.name LIKE %:keyword%")
    Page<WomenItem> findByBrandOrName(@Param("keyword") String keyword, Pageable pageable);
}


