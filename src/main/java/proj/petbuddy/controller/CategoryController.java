package proj.petbuddy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import proj.petbuddy.domain.item.Item;
import proj.petbuddy.repository.board.CategoryRepository;
import proj.petbuddy.repository.item.ItemRepository;
import proj.petbuddy.service.category.CategoryService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    /**
     * (여성)카테고리별 목록
     **/
    @GetMapping("/women/{category}")
    public String totalWomen(@PathVariable("category") String category, Model model, @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Item> womenItems = categoryRepository.womenFindByCategoryParentIdAndName(category, pageable);

        /** 페이징 처리 **/
        if (!womenItems.isEmpty()) {

            int pageNum = womenItems.getPageable().getPageNumber(); // 현재 페이지
            int totalPages = womenItems.getTotalPages();// 총 페이지수
            int pageBlock = 5; // 블럭의 수
            int startBlockPage = ((pageNum - 1) / pageBlock) * pageBlock + 1;
            int endBlockPage = startBlockPage + pageBlock - 1;
            endBlockPage = totalPages < endBlockPage ? totalPages : endBlockPage;

            model.addAttribute("womenItems", womenItems);
            model.addAttribute("currentPage", pageNum);
            model.addAttribute("startBlockPage", startBlockPage);
            model.addAttribute("endBlockPage", endBlockPage);
        }
        return "list/women";
    }

    /**
     * (남성)카테고리별 목록
     **/
    @GetMapping("/men/{category}")
    public String menCate(@PathVariable("category") String category, Model model, @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Item> menItems = categoryRepository.menFindByCategoryParentIdAndName(category, pageable);

        /** 페이징 처리 **/
        if (!menItems.isEmpty()) {

            int pageNum = menItems.getPageable().getPageNumber(); // 현재 페이지
            int totalPages = menItems.getTotalPages();// 총 페이지수
            int pageBlock = 5; // 블럭의 수
            int startBlockPage = ((pageNum - 1) / pageBlock) * pageBlock + 1;
            int endBlockPage = startBlockPage + pageBlock - 1;
            endBlockPage = totalPages < endBlockPage ? totalPages : endBlockPage;

            model.addAttribute("menItems", menItems);
            model.addAttribute("currentPage", pageNum);
            model.addAttribute("startBlockPage", startBlockPage);
            model.addAttribute("endBlockPage", endBlockPage);
        }
        return "list/men";
    }


}

