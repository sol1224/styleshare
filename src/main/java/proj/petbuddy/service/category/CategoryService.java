package proj.petbuddy.service.category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.petbuddy.domain.item.WomenItem;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    @PersistenceContext
    private EntityManager entityManager;


    public List<WomenItem> getWomenItemByCategory(String category) {
        String jpql = "SELECT w FROM WomenItem w WHERE w.category.name = :category";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("category", category);
        return query.getResultList();
    }
}