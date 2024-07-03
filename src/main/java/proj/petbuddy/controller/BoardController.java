package proj.petbuddy.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import proj.petbuddy.domain.board.Board;
import proj.petbuddy.domain.board.Notice;
import proj.petbuddy.domain.mypage.Member;
import proj.petbuddy.dto.board.BoardRequestDTO;
import proj.petbuddy.dto.board.BoardResponseDTO;
import proj.petbuddy.dto.board.CommentDTO;
import proj.petbuddy.repository.board.BoardRepository;
import proj.petbuddy.repository.board.FAQRepository;
import proj.petbuddy.repository.board.NoticeRepository;
import proj.petbuddy.repository.member.MemberRepository;
import proj.petbuddy.service.board.BoardService;
import proj.petbuddy.service.board.SearchBoardService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final FAQRepository faqRepository;
    private final BoardService boardService;
    private final NoticeRepository noticeRepository;
    private final SearchBoardService searchBoardService;
    private final MemberRepository memberRepository;

/** =============== 게시판 =============== **/

    /**
     * 신규 게시글 등록
     **/
    @GetMapping("/board/boardWrite")
    public String write(@ModelAttribute BoardRequestDTO boardFormDto, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("boardFormDto", new BoardRequestDTO());
        return "board/boardWrite";
    }

    @PostMapping("/board/boardWrite")
    public String save(@Valid @ModelAttribute("boardFormDto") BoardRequestDTO boardFormDto, BindingResult bindingResult, @AuthenticationPrincipal User user, Model model) {

        if (bindingResult.hasErrors()) {
            return "board/boardWrite";
        }
        try {
            boardService.boardSave(user.getUsername(), boardFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "board/boardWrite";
        }
        return "redirect:/board";
    }


    /**
     * 게시판 전체 목록
     **/
    @GetMapping("/board")
    public String board(Model model, @PageableDefault(page = 0, size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @AuthenticationPrincipal User user,
                        @RequestParam(name = "page", defaultValue = "1") int page) {
        /** 아이템 **/
        Page<Board> board = boardRepository.findByBoard(pageable);
        model.addAttribute("board", board);

        /** 페이징 처리 **/
        Page<Board> boards = boardRepository.findByBoard(pageable);

        if (!boards.isEmpty()) {
            int pageNum = boards.getPageable().getPageNumber(); // 현재 페이지
            int totalPages = boards.getTotalPages(); // 총 페이지수
            int pageBlock = 5; // 블럭의 수
            int startBlockPage = ((pageNum) / pageBlock) * pageBlock + 1; // 수정된 부분
            int endBlockPage = startBlockPage + pageBlock - 1;
            endBlockPage = Math.min(totalPages, endBlockPage); // 수정된 부분

            model.addAttribute("user", user);
            model.addAttribute("currentPage", pageNum);
            model.addAttribute("boards", boards);
            model.addAttribute("startBlockPage", startBlockPage);
            model.addAttribute("endBlockPage", endBlockPage);
            model.addAttribute("totalPages", totalPages); // 추가된 부분
        }

        return "board/board";
    }

    /**
     * 게시판 전체 목록
     **/
    @GetMapping("/news")
    public String news(Model model, @PageableDefault(page = 0, size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(name = "page", defaultValue = "1") int page) {
        /** 페이징 처리 **/
        Page<Board> boards = boardRepository.findByBoardNews(pageable);

        if (!boards.isEmpty()) {
            int pageNum = boards.getPageable().getPageNumber(); // 현재 페이지
            int totalPages = boards.getTotalPages(); // 총 페이지수
            int pageBlock = 5; // 블럭의 수
            int startBlockPage = ((pageNum) / pageBlock) * pageBlock + 1; // 수정된 부분
            int endBlockPage = startBlockPage + pageBlock - 1;
            endBlockPage = Math.min(totalPages, endBlockPage); // 수정된 부분

            model.addAttribute("currentPage", pageNum);
            model.addAttribute("boards", boards);
            model.addAttribute("startBlockPage", startBlockPage);
            model.addAttribute("endBlockPage", endBlockPage);
            model.addAttribute("totalPages", totalPages); // 추가된 부분
        }

        return "board/news";
    }


    /**
     * 특정 게시글 목록
     **/
    @GetMapping("/board/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        BoardResponseDTO board = boardService.getBoard(id);
        CommentDTO comment = new CommentDTO();

        List<CommentDTO> comments = board.getComments();



        // 사용자 정보와 게시글 작성자 정보가 일치하는지 확인하여 수정 버튼을 보이도록 설정
        boolean isAuthor = false; // 기본적으로 작성자가 아니라고 가정

        if (user != null && board.getMember() != null) {
            // 사용자 정보가 존재하고, 게시글의 작성자 정보도 존재하는 경우에만 비교
            isAuthor = board.getMember().getId().equals(user.getUsername());
        } else if (board.getMember() == null) {
            // 게시글 작성자 정보가 null인 경우, 작성자로 간주하지 않음
            isAuthor = false;
        }

        model.addAttribute("user", user);

        model.addAttribute("isAuthor", isAuthor);
        model.addAttribute("comments", comments);
        model.addAttribute("board", board);
        model.addAttribute("comment", comment);

        return "board/boardDetail";
    }


    /**
     * 특정 게시글 삭제
     **/
    @GetMapping("/board/delete")
    public String boardDelete(Long id, BoardRequestDTO requestDTO) {
        BoardResponseDTO findBoard = boardService.deleteBoard(id, requestDTO);
        return "redirect:/board";
    }

    /**
     * 특정 게시물 수정
     **/
    @GetMapping("/board/update/{id}")
    public String update(Model model, @PathVariable("id") Long id) {
        BoardResponseDTO board = boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board/boardUpdate";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Long id, @ModelAttribute BoardRequestDTO requestDTO, Model model, Member member) {
        Long board = boardService.updateBoard(id, requestDTO, member);
        model.addAttribute("board", board);
        return "redirect:/board/detail/{id}";
    }


    /**
     * =============== 공지 사항 ===============
     **/

    @GetMapping("/notice")
    public String notice(Model model, @PageableDefault(page = 0, size = 15, sort = "seq", direction = Sort.Direction.DESC) Pageable pageable) {
        /** 페이징 처리 **/
        Page<Notice> boards = noticeRepository.findAll(pageable);

        int pageNum = boards.getPageable().getPageNumber() - 1; // 현재 페이지
        int totalPages = boards.getTotalPages();// 총 페이지수
        int pageBlock = 5; // 블럭의 수
        int startBlockPage = (pageNum / pageBlock) * pageBlock + 1;
        int endBlockPage = startBlockPage + pageBlock - 1;
        endBlockPage = totalPages < endBlockPage ? totalPages : endBlockPage;

        model.addAttribute("boards", boards);
        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);
        return "board/notice";
    }


    /**
     * =============== 회원 혜택 안내 ===============
     **/
    @GetMapping("/benefit")
    public String benefit() {
        return "board/memberBenefit";
    }


    /**
     * =============== 자주 묻는 질문 ===============
     **/

    @GetMapping("/faq")
    public String faq(Model model, @PageableDefault(page = 0, size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        /** 페이징 처리 **/
        Page<Board> boards = boardRepository.findByBoardFA(pageable);

        int pageNum = boards.getPageable().getPageNumber() - 1; // 현재 페이지
        int totalPages = boards.getTotalPages();// 총 페이지수
        int pageBlock = 5; // 블럭의 수
        int startBlockPage = (pageNum / pageBlock) * pageBlock + 1;
        int endBlockPage = startBlockPage + pageBlock - 1;
        endBlockPage = totalPages < endBlockPage ? totalPages : endBlockPage;

        model.addAttribute("boards", boards);
        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);

        return "board/faq";
    }


    /**
     * =============== 1:1 문의 ===============
     **/

    @GetMapping("/privateboard")
    public String privateboard(@ModelAttribute("member") Member member) {
        return "board/privateboard";
    }


    /**
     * =============== 검색 기능 ===============
     **/
    /**
     * 검색
     **/
    @GetMapping("/board/search")
    public String searchService(String keyword, Model model, @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {


        /** 페이징 처리 **/
        Page<Board> boards = searchBoardService.search(keyword, pageable);


        int pageNum = boards.getPageable().getPageNumber(); // 현재 페이지
        int totalPages = boards.getTotalPages(); // 총 페이지수
        int pageBlock = 5; // 블럭의 수
        int startBlockPage = ((pageNum) / pageBlock) * pageBlock + 1; // 수정된 부분
        int endBlockPage = startBlockPage + pageBlock - 1;
        endBlockPage = Math.min(totalPages, endBlockPage); // 수정된 부분

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("boards", boards);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);
        model.addAttribute("totalPages", totalPages); // 추가된 부분


        return "board/search";
    }


}