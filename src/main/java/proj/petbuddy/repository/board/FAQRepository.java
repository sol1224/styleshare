package proj.petbuddy.repository.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import proj.petbuddy.domain.board.FAQ;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
    Page<FAQ> findAll(Pageable pageable);
}
