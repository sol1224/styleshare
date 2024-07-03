package proj.petbuddy.repository.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proj.petbuddy.domain.item.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findAll(Pageable pageable);


    @Query("SELECT i FROM Item i WHERE i.id = :id")
    Page<Item> findById(@Param("id") Long id, Pageable pageable);

/*    @Query("SELECT i FROM Item i WHERE i.id = :id")
    Item findById(@Param("id") Long id, Pageable pageable);*/


    /**
     * 전체
     **/
    /**
     * 신상
     **/
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id ORDER BY i.createdAt DESC")
    Page<Item> findByAllOrderByCreatedAt(Pageable pageable);

    /**
     * 여성
     **/
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 1")
    Page<Item> findByAllWomen(Pageable pageable);

    /**
     * 남성
     **/
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 2")
    Page<Item> findByAllMen(Pageable pageable);

    /**
     * 라이프
     **/
     @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE i.category.id = 3")
    Page<Item> findByAllLife(Pageable pageable);



/**
 * --------------------------------------------------------------- **/


    /**
     * 검색창 (남성_브랜드 or 상품명)
     **/
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE i.brand = :keyword OR i.name LIKE %:keyword%")
    Page<Item> findByBrandOrName(@Param("keyword") String keyword, Pageable pageable);


    /**
     * --------------------------------------------------------------- **/

    /** 셀렉트 옵션 선택(가격) **/
    /** 여성 **/
    // 100,000원 이하의 상품 찾기
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 1 AND i.price < :price")
    Page<Item> findByPriceLessThanEqual(@Param("price") int price, Pageable pageable);
    // 100,000원 이상 250,000원 이하의 상품 찾기
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 1 AND i.price > :minPrice AND i.price < :maxPrice")
    Page<Item> findByPriceBetween(@Param("minPrice") int minPrice,@Param("maxPrice") int maxPrice, Pageable pageable);
    // 250,000원 이상의 상품 찾기
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 1 AND i.price < :price")
    Page<Item> findByPriceGreaterThanEqual(@Param("price") int price, Pageable pageable);

    /** 남성 **/
    // 100,000원 이하의 상품 찾기
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 2 AND i.price < :price")
    Page<Item> findByPriceLessThanEqualMan(@Param("price") int price, Pageable pageable);
    // 100,000원 이상 250,000원 이하의 상품 찾기
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 2 AND i.price > :minPrice AND i.price < :maxPrice")
    Page<Item> findByPriceBetweenMan(@Param("minPrice") int minPrice,@Param("maxPrice") int maxPrice, Pageable pageable);
    // 250,000원 이상의 상품 찾기
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 2 AND i.price < :price")
    Page<Item> findByPriceGreaterThanEqualMan(@Param("price") int price, Pageable pageable);

    /** 라이프 **/
    // 100,000원 이하의 상품 찾기
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.id = 3 AND i.price < :price")
    Page<Item> findByPriceLessThanEqualLife(@Param("price") int price, Pageable pageable);
    // 100,000원 이상 250,000원 이하의 상품 찾기
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.id = 3 AND i.price > :minPrice AND i.price < :maxPrice")
    Page<Item> findByPriceBetweenLife(@Param("minPrice") int minPrice,@Param("maxPrice") int maxPrice, Pageable pageable);
    // 250,000원 이상의 상품 찾기
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.id = 3 AND i.price < :price")
    Page<Item> findByPriceGreaterThanEqualLife(@Param("price") int price, Pageable pageable);


    /** 셀렉트 옵션 선택(정렬) **/
    /** 여성 **/
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 1 ORDER BY i.price DESC")
    Page<Item> findAllByOrderByPriceDesc(Pageable pageable);
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 1 ORDER BY i.price ASC")
    Page<Item> findAllByOrderByPriceAsc(Pageable pageable);
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 1 ORDER BY i.heart DESC")
    Page<Item> findAllByOrderByHeartDes(Pageable pageable);

    /** 남성 **/
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 2 ORDER BY i.price DESC")
    Page<Item> findAllByOrderByPriceDescMan(Pageable pageable);
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 2 ORDER BY i.price ASC")
    Page<Item> findAllByOrderByPriceAscMan(Pageable pageable);
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 2 ORDER BY i.heart DESC")
    Page<Item> findAllByOrderByHeartDesMan(Pageable pageable);

    /** 라이프 **/
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.id = 3 ORDER BY i.price DESC")
    Page<Item> findAllByOrderByPriceDescLife(Pageable pageable);
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.id = 3 ORDER BY i.price ASC")
    Page<Item> findAllByOrderByPriceAscLife(Pageable pageable);
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.id = 3 ORDER BY i.heart DESC")
    Page<Item> findAllByOrderByHeartDesLife(Pageable pageable);


    /** 셀렉트 옵션 선택(정렬) **/
    /** 베스트 **/
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id ORDER BY i.heart DESC")
    Page<Item> findAllByBestItem(Pageable pageable);

    /** 세일 **/
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id ORDER BY i.discount DESC")
    Page<Item> findAllBySaleItem(Pageable pageable);

}
