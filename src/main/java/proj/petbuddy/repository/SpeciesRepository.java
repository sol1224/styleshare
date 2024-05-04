package proj.petbuddy.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import proj.petbuddy.domain.item.WomenItem;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SpeciesRepository {

    private final EntityManager em;

    /**
     * 저장
     **/
    public void save(WomenItem womenItem) {
        em.persist(womenItem);
    }

    /**
     * 아이디(PK) 조회
     **/
    public WomenItem findOne(Long seq) {
        return em.find(WomenItem.class, seq);
    }

    /**
     * 이름 조회
     **/
    public List<WomenItem> findByName(String name) {
        return em.createQuery("select s from WomenItem s where s.name = :name", WomenItem.class)
                .setParameter("name", name)
                .getResultList();
    }

    /**
     * 전체 조회
     **/
    public List<WomenItem> findAll() {
        return em.createQuery("select s from WomenItem s", WomenItem.class)
                .getResultList();
    }

}
