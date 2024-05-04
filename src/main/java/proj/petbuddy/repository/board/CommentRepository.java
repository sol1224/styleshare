package proj.petbuddy.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.petbuddy.domain.board.Board;
import proj.petbuddy.domain.board.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // select * from comment_table where board_id=? order by id desc;
    List<Comment> findAllByBoardOrderByIdDesc(Board board);
}
