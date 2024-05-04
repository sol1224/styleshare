package proj.petbuddy.repository.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import proj.petbuddy.domain.item.ItemImg;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {


    @Query("SELECT i FROM ItemImg img JOIN img.item i JOIN i.category c WHERE c.parent.id = 1")
    Page<ItemImg> findAll(Pageable pageable);

//    SELECT * FROM ITEM_IMG JOIN ITEM ON ITEM.ID = ITEM_IMG.ITEM_ID JOIN CATEGORY ON ITEM.CATEGORY_ID = CATEGORY.id;

    @Query("SELECT i FROM Item i JOIN Category c ON i.category.id = c.id WHERE c.parent.id = 2")
    Page<ItemImg> findAllMen(Pageable pageable);




    ItemImg findByItemId(Long Id);

//    Page<ItemImg> findBySpecies_Name(String keyword, Pageable pageable);}


/*    @Query("select i from ItemImg i left join i.species s where s.name = :speciesName")
    Page<ItemImg> findBySpeciesName(@Param("speciesName") String speciesName, Pageable pageable);
}*/


//    @Query("select i from ItemImg i left join i.item wi where wi.name = :keyword")
//    Page<ItemImg> findBySpeciesName(String keyword, Pageable pageable);
}