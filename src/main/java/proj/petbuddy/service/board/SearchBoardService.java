package proj.petbuddy.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.petbuddy.domain.board.Board;
import proj.petbuddy.repository.board.BoardRepository;

@RequiredArgsConstructor
@Transactional
@Service
public class SearchBoardService {

    private final BoardRepository boardRepository;

    /**
     * 검색 서비스 (제목)
     **/
    @Transactional
    public Page<Board> search(String keyword, Pageable pageable) {
        Page<Board> list = boardRepository.findByTitleContaining(keyword, pageable);
        return list;
    }
}
