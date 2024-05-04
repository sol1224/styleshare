package proj.petbuddy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import proj.petbuddy.dto.board.CommentDTO;
import proj.petbuddy.service.board.BoardService;
import proj.petbuddy.service.board.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO, Long id, @AuthenticationPrincipal User user) {
        if (user == null || user.getUsername() == null) {
            // 사용자가 로그인되어 있지 않거나 사용자 이름이 null인 경우 로그인 페이지로 리다이렉트
            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        Long saveResult = commentService.save(commentDTO, id, user.getUsername());
        if (saveResult != null) {
            // 작성 성공 시 댓글 목록을 가져와서 리턴
            // 댓글 목록: 해당 게시글의 댓글 전체
            List<CommentDTO> commentList = commentService.findAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
