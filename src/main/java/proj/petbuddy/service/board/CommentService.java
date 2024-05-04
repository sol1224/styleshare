package proj.petbuddy.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.petbuddy.domain.mypage.Member;
import proj.petbuddy.domain.board.Board;
import proj.petbuddy.domain.board.Comment;
import proj.petbuddy.dto.board.CommentDTO;
import proj.petbuddy.repository.board.BoardRepository;
import proj.petbuddy.repository.board.CommentRepository;
import proj.petbuddy.repository.member.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(CommentDTO commentDTO, Long boardId, String nickname) {

        /** 부모 엔터티 조회(Board) **/
        Member user = memberRepository.findById(nickname);
        if (user != null) {
            commentDTO.setMember(user);
        } else {
            return null;
        }

        Optional<Board> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());

        if (optionalBoardEntity.isPresent()) {
            Board boardEntity = optionalBoardEntity.get();
            Comment commentEntity = Comment.toSaveEntity(commentDTO, boardEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }

    @Transactional
    public List<CommentDTO> findAll(Long boardId) {
        // select * from comment_table where board_id=? order by id desc;
        /** 부모 엔터티 조회(Board) **/
        Board boardEntity = boardRepository.findById(boardId).get();

        List<Comment> commentEntityList = commentRepository.findAllByBoardOrderByIdDesc(boardEntity);
        /** Entity -> Dto **/
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : commentEntityList) {
            CommentDTO requestDTO = CommentDTO.toCommentDTO(comment, boardId);
            commentDTOList.add(requestDTO);
        }
        return commentDTOList;
    }


}
