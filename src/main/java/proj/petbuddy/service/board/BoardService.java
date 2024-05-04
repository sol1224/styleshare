package proj.petbuddy.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.petbuddy.domain.mypage.Member;
import proj.petbuddy.domain.board.Board;
import proj.petbuddy.dto.board.BoardRequestDTO;
import proj.petbuddy.dto.board.BoardResponseDTO;
import proj.petbuddy.repository.board.BoardRepository;
import proj.petbuddy.repository.member.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    /**
     * 게시판 글 생성
     */
    @Transactional
    public BoardResponseDTO boardSave(String id, BoardRequestDTO requestDto) {

        Member member = memberRepository.findById(id);
        Board board = new Board(requestDto);
        board.setMember(member);

        Board saveBoard = boardRepository.save(board);
        return new BoardResponseDTO(saveBoard);
    }


    /**
     * 전체 게시판 조회
     */
    public List<BoardResponseDTO> getBoardList() {
        return boardRepository.findAll().stream() // DB 에서 조회한 List -> stream 으로 변환
                .map(BoardResponseDTO::new) // stream 처리를 통해, Board 객체 -> BoardResponseDto 로 변환
                .toList(); // 다시 stream -> List 로 변환
    }

    /**
     * 특정 게시글 조회
     */
    public BoardResponseDTO getBoard(Long seq) {
        Board board = boardRepository.findById(seq).orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        // 해당 seq 가 존재할 경우
        return new BoardResponseDTO(board);
    }

    /**
     * 특정 게시글 삭제
     */
    @Transactional
    public BoardResponseDTO deleteBoard(Long seq, BoardRequestDTO requestDTO) {
        Board board = boardRepository.findById(seq).get();

        // 비밀번호 일치 확인
        if ((board.getPassword() == null && requestDTO.getPassword() == null) ||
                (board.getPassword() != null && board.getPassword().equals(requestDTO.getPassword()))) {
            boardRepository.deleteById(seq);
        } else {
            return new BoardResponseDTO();
        }
        return new BoardResponseDTO(board);
    }


    /**
     * 게시글 수정
     */
    @Transactional
    public Long updateBoard(Long seq, BoardRequestDTO requestDTO, Member member) {
        Board board = boardRepository.findById(seq).orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다"));
        board.update(requestDTO.getTitle(), requestDTO.getContent());
        boardRepository.save(board);
        return board.getId();
    }


}


