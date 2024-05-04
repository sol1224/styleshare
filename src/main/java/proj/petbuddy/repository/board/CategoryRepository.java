package proj.petbuddy.repository.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proj.petbuddy.domain.category.Category;
import proj.petbuddy.domain.item.Item;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 카테고리별 아이템 (아우터/티셔츠/팬츠~)
     **/
    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 1 AND c.cateName = :category")
    Page<Item> womenFindByCategoryParentIdAndName(@Param("category") String category, Pageable pageable);

    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 2 AND c.cateName = :category")
    Page<Item> menFindByCategoryParentIdAndName(@Param("category") String category, Pageable pageable);



}