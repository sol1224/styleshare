package proj.petbuddy.repository.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import proj.petbuddy.domain.board.Board;
import proj.petbuddy.domain.item.Item;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAll(Pageable pageable);

    Page<Board> findByTitleContaining(String keyword, Pageable pageable);

    @Query("SELECT b FROM Board b where b.boardType = null")
    Page<Board> findByBoard(Pageable pageable);

    @Query("SELECT b FROM Board b where b.boardType = 'NOTICE'")
    Page<Board> findByBoardNews(Pageable pageable);

    @Query("SELECT b FROM Board b where b.boardType = 'FREE'")
    Page<Board> findByBoardFA(Pageable pageable);

}
