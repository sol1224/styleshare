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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import proj.petbuddy.domain.item.Item;
import proj.petbuddy.domain.item.ItemImg;
import proj.petbuddy.domain.stutus.Display;
import proj.petbuddy.domain.stutus.Gender;
import proj.petbuddy.domain.stutus.SellStatus;
import proj.petbuddy.dto.item.ItemDetailResponse;
import proj.petbuddy.dto.item.ItemFormDTO;
import proj.petbuddy.repository.item.ItemImgRepository;
import proj.petbuddy.repository.item.ItemRepository;
import proj.petbuddy.service.item.ItemService;
import proj.petbuddy.service.search.SearchService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public class ItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final SearchService searchService;
    private final ItemImgRepository itemImgRepository;


    /**
     * ENUM 사용
     **/
    @ModelAttribute("genders")
    public Gender[] genders() {
        return Gender.values();
    }

    @ModelAttribute("displays")
    public Display[] displays() {
        return Display.values();
    }

    @ModelAttribute("sellStatuses")
    public SellStatus[] sellStatuses() {
        return SellStatus.values();
    }

    /**
     * ============================================================================
     **/

    /**
     * 신규상품 등록
     **/

    @GetMapping(value = "/newItem")
    public String itemForm(Model model, Pageable pageable, @AuthenticationPrincipal User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            return "redirect:/login"; // 사용자가 로그인되어 있지 않으면 로그인 페이지로 리다이렉트
        } else {
            model.addAttribute("itemFormDto", new ItemFormDTO());
            return "item/itemForm"; // 사용자가 로그인되어 있으면 상품 등록 페이지로 이동
        }
    }

    public class YourDTOClass {
        private String selectedValue;

        public String getSelectedValue() {
            return selectedValue;
        }

        public void setSelectedValue(String selectedValue) {
            this.selectedValue = selectedValue;
        }
    }

    @PostMapping(value = "/newItem")
    public String itemNew(@RequestParam("selectedValue") String selectedValue,
                          @ModelAttribute("itemFormDto") @Valid ItemFormDTO itemFormDto,
                          BindingResult bindingResult,
                          @AuthenticationPrincipal User user,
                          Model model,
                          @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,
                          Pageable pageable) {

        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            return "redirect:/login"; // 사용자가 로그인되어 있지 않으면 로그인 페이지로 리다이렉트
        }

        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }

        if (itemImgFileList.isEmpty() && itemFormDto.getSeq() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값입니다.");
            return "item/itemForm";
        }

        try {
            Long longValue = Long.parseLong(selectedValue);
            itemService.saveItem(itemFormDto, itemImgFileList, longValue);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/women"; // 상품 등록 후 여성 페이지로 리다이렉트
    }

    /**
     * -----------------------------------------------------------
     **/
    /**
     * 서브 페이지_전체 목록
     **/

    @GetMapping("/new")
    public String newITem(Model model, @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        /** 페이징 처리 **/
        Page<Item> items = itemRepository.findByAllOrderByCreatedAt(pageable);


        if (!items.isEmpty()) {

            int pageNum = items.getPageable().getPageNumber(); // 현재 페이지
            int totalPages = items.getTotalPages(); // 총 페이지수
            int pageBlock = 5; // 블럭의 수
            int startBlockPage = ((pageNum) / pageBlock) * pageBlock + 1; // 수정된 부분
            int endBlockPage = startBlockPage + pageBlock - 1;
            endBlockPage = Math.min(totalPages, endBlockPage); // 수정된 부분

            model.addAttribute("currentPage", pageNum);
            model.addAttribute("items", items);
            model.addAttribute("startBlockPage", startBlockPage);
            model.addAttribute("endBlockPage", endBlockPage);
            model.addAttribute("totalPages", totalPages); // 추가된 부분
        }
        return "list/new";
    }

    @GetMapping("/women")
    public String women(Model model, @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        /** 페이징 처리 **/
        Page<Item> womenItems = itemRepository.findByAllWomen(pageable);

        if (!womenItems.isEmpty()) {

            int pageNum = womenItems.getPageable().getPageNumber(); // 현재 페이지
            int totalPages = womenItems.getTotalPages(); // 총 페이지수
            int pageBlock = 5; // 블럭의 수
            int startBlockPage = ((pageNum) / pageBlock) * pageBlock + 1; // 수정된 부분
            int endBlockPage = startBlockPage + pageBlock - 1;
            endBlockPage = Math.min(totalPages, endBlockPage); // 수정된 부분

            model.addAttribute("currentPage", pageNum);
            model.addAttribute("womenItems", womenItems);
            model.addAttribute("startBlockPage", startBlockPage);
            model.addAttribute("endBlockPage", endBlockPage);
            model.addAttribute("totalPages", totalPages); // 추가된 부분
        }

        return "list/women";
    }


    @GetMapping("/men")
    public String men(Model model, @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        /** 아이템 **/
        Page<ItemImg> menItem = itemImgRepository.findAllMen(pageable);
        model.addAttribute("menItem", menItem);

        /** 페이징 처리 **/
        Page<Item> menItems = itemRepository.findByAllMen(pageable);

        if (!menItems.isEmpty()) {
            int pageNum = menItems.getPageable().getPageNumber(); // 현재 페이지
            int totalPages = menItems.getTotalPages(); // 총 페이지수
            int pageBlock = 5; // 블럭의 수
            int startBlockPage = ((pageNum) / pageBlock) * pageBlock + 1; // 수정된 부분
            int endBlockPage = startBlockPage + pageBlock - 1;
            endBlockPage = Math.min(totalPages, endBlockPage); // 수정된 부분

            model.addAttribute("currentPage", pageNum);
            model.addAttribute("menItems", menItems);
            model.addAttribute("startBlockPage", startBlockPage);
            model.addAttribute("endBlockPage", endBlockPage);
            model.addAttribute("totalPages", totalPages); // 추가된 부분
        }
        return "list/men";
    }

    @GetMapping("/life")
    public String life(Model model, @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        /** 페이징 처리 **/
        Page<Item> lifes = itemRepository.findByAllLife(pageable);

        if (!lifes.isEmpty()) {
            int pageNum = lifes.getPageable().getPageNumber(); // 현재 페이지
            int totalPages = lifes.getTotalPages(); // 총 페이지수
            int pageBlock = 5; // 블럭의 수
            int startBlockPage = ((pageNum) / pageBlock) * pageBlock + 1; // 수정된 부분
            int endBlockPage = startBlockPage + pageBlock - 1;
            endBlockPage = Math.min(totalPages, endBlockPage); // 수정된 부분

            model.addAttribute("currentPage", pageNum);
            model.addAttribute("lifes", lifes);
            model.addAttribute("startBlockPage", startBlockPage);
            model.addAttribute("endBlockPage", endBlockPage);
            model.addAttribute("totalPages", totalPages); // 추가된 부분
        }
        return "list/life";
    }


    /**
     * -----------------------------------------------------------
     **/
    /**
     * 상세 조회
     **/

    @GetMapping("/new/detail/{id}")
    public String newDetail(Model model, @PathVariable("id") Long id) {
        ItemDetailResponse item = itemService.itemDetail(id);
        model.addAttribute("item", item);
        return "list/itemDetail";
    }

    @GetMapping("/women/detail/{id}")
    public String womenDetail(Model model, @PathVariable("id") Long id) {
        ItemDetailResponse item = itemService.itemDetail(id);
        model.addAttribute("item", item);
        return "list/itemDetail";
    }

    @GetMapping("/men/detail/{id}")
    public String menDetail(Model model, @PathVariable("id") Long id) {
        ItemDetailResponse item = itemService.itemDetail(id);
        model.addAttribute("item", item);
        return "list/itemDetail";
    }

    @GetMapping("/life/detail/{id}")
    public String lifeDetail(Model model, @PathVariable("id") Long id) {
        ItemDetailResponse item = itemService.itemDetail(id);
        model.addAttribute("item", item);
        return "list/itemDetail";
    }


    /**
     * -----------------------------------------------------------
     **/
    /**
     * 게시글 검색(제목,내용)
     **/

    @GetMapping("/search")
    public String searchService(String keyword, Model model, @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {


        /** 페이징 처리 **/
        Page<Item> items = searchService.search(keyword, pageable);


        int pageNum = items.getPageable().getPageNumber(); // 현재 페이지
        int totalPages = items.getTotalPages(); // 총 페이지수
        int pageBlock = 5; // 블럭의 수
        int startBlockPage = ((pageNum) / pageBlock) * pageBlock + 1; // 수정된 부분
        int endBlockPage = startBlockPage + pageBlock - 1;
        endBlockPage = Math.min(totalPages, endBlockPage); // 수정된 부분

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("items", items);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);
        model.addAttribute("totalPages", totalPages); // 추가된 부분


        return "list/search";
    }

    /**
     * -----------------------------------------------------------
     **/
    /**
     * 셀렉트박스 (가격.정렬)
     **/

    @GetMapping("/women/select")
    public String selectOption(@RequestParam("value") String value, Model model, @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Item> items = null;

        switch (value) {
            case "selectOption1":
                // "100,000원 이하"의 경우
                items = itemRepository.findByPriceLessThanEqual(100000, pageable);
                break;
            case "selectOption2":
                // "100,000원 ~ 150,000원"의 경우
                items = itemRepository.findByPriceBetween(100000, 150000, pageable);
                break;
            case "selectOption3":
                // "150,000원 ~ 200,000원"의 경우
                items = itemRepository.findByPriceBetween(150000, 200000, pageable);
                break;
            case "selectOption4":
                // "200,000원 ~
                items = itemRepository.findByPriceGreaterThanEqual(200000, pageable);
                break;
            default:
        }
        addPagingAttributes(model, items);
        model.addAttribute("value", value);
        model.addAttribute("path", "women");

        return "list/selectItem";
    }

    @GetMapping("/men/select")
    public String selectOptionMen(@RequestParam("value") String value, Model model, @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Item> items = null;

        switch (value) {
            case "selectOption1":
                // "100,000원 이하"의 경우
                items = itemRepository.findByPriceLessThanEqualMan(100000, pageable);
                break;
            case "selectOption2":
                // "100,000원 ~ 150,000원"의 경우
                items = itemRepository.findByPriceBetweenMan(100000, 150000, pageable);
                break;
            case "selectOption3":
                // "150,000원 ~ 200,000원"의 경우
                items = itemRepository.findByPriceBetweenMan(150000, 200000, pageable);
                break;
            case "selectOption4":
                // "200,000원 ~
                items = itemRepository.findByPriceGreaterThanEqualMan(200000, pageable);
                break;
            default:
        }
        addPagingAttributes(model, items);
        model.addAttribute("value", value);
        model.addAttribute("path", "men");

        return "list/selectItemM";
    }

    @GetMapping("/life/select")
    public String selectOptionLife(@RequestParam("value") String value, Model model, @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Item> items = null;

        switch (value) {
            case "selectOption1":
                // "100,000원 이하"의 경우
                items = itemRepository.findByPriceLessThanEqualLife(100000, pageable);
                break;
            case "selectOption2":
                // "100,000원 ~ 150,000원"의 경우
                items = itemRepository.findByPriceBetweenLife(100000, 150000, pageable);
                break;
            case "selectOption3":
                // "150,000원 ~ 200,000원"의 경우
                items = itemRepository.findByPriceBetweenLife(150000, 200000, pageable);
                break;
            case "selectOption4":
                // "200,000원 ~ 250,000원"의 경우
                items = itemRepository.findByPriceBetweenLife(200000, 250000, pageable);
                break;
            case "selectOption5":
                // "250,000원 이상"의 경우
                items = itemRepository.findByPriceGreaterThanEqualLife(250000, pageable);
                break;
            default:

        }
        addPagingAttributes(model, items);
        model.addAttribute("value", value);
        model.addAttribute("path", "life");

        return "list/selectItemL";
    }

    private static void addPagingAttributes(Model model, Page<Item> items) {
        if (items != null && !items.isEmpty()) {
            int pageNum = items.getPageable().getPageNumber(); // 현재 페이지
            int totalPages = items.getTotalPages(); // 총 페이지수
            int pageBlock = 5; // 블럭의 수
            int startBlockPage = ((pageNum) / pageBlock) * pageBlock + 1; // 수정된 부분
            int endBlockPage = startBlockPage + pageBlock - 1;
            endBlockPage = Math.min(totalPages, endBlockPage); // 수정된 부분

            model.addAttribute("currentPage", pageNum);
            model.addAttribute("startBlockPage", startBlockPage);
            model.addAttribute("endBlockPage", endBlockPage);
            model.addAttribute("totalPages", totalPages); // 추가된 부분
        }
        model.addAttribute("womenItems", items);
    }

    @GetMapping("/women/selectSell")
    public String selectOption2(@RequestParam("value") String value, Model model, @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Item> items = null;

        switch (value) {

            case "selectOption2-2":
                items = itemRepository.findAllByOrderByHeartDes(pageable);
                break;
            case "selectOption2-3":
                items = itemRepository.findAllByOrderByPriceDesc(pageable);
                break;
            case "selectOption2-4":
                items = itemRepository.findAllByOrderByPriceAsc(pageable);
                break;
            case "selectOption2-5":
                items = itemRepository.findAllByOrderByHeartDes(pageable);
                break;
            default:
        }
        addPagingAttributes(model, items);
        model.addAttribute("value", value);
        model.addAttribute("path", "women");

        return "list/selectItem2";
    }

    @GetMapping("/men/selectSell")
    public String selectOption2Man(@RequestParam("value") String value, Model model, @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Item> items = null;

        switch (value) {

            case "selectOption2-2":
                items = itemRepository.findAllByOrderByHeartDesMan(pageable);
                break;
            case "selectOption2-3":
                items = itemRepository.findAllByOrderByPriceDescMan(pageable);
                break;
            case "selectOption2-4":
                items = itemRepository.findAllByOrderByPriceAscMan(pageable);
                break;
            case "selectOption2-5":
                items = itemRepository.findAllByOrderByHeartDesMan(pageable);
                break;
            default:
        }
        addPagingAttributes(model, items);
        model.addAttribute("value", value);
        model.addAttribute("path", "women");

        return "list/selectItem2M";
    }

    @GetMapping("/life/selectSell")
    public String selectOption2Life(@RequestParam("value") String value, Model model, @PageableDefault(size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Item> items = null;

        switch (value) {

            case "selectOption2-2":
                items = itemRepository.findAllByOrderByHeartDesLife(pageable);
                break;
            case "selectOption2-3":
                items = itemRepository.findAllByOrderByPriceDescLife(pageable);
                break;
            case "selectOption2-4":
                items = itemRepository.findAllByOrderByPriceAscLife(pageable);
                break;
            case "selectOption2-5":
                items = itemRepository.findAllByOrderByHeartDesLife(pageable);
                break;
            default:
        }
        addPagingAttributes(model, items);
        model.addAttribute("value", value);
        model.addAttribute("path", "life");

        return "list/selectItem2L";
    }


    /**
     * -----------------------------------------------------------
     **/
    /**
     * 베스트 (정렬)
     **/

    @GetMapping("/best")
    public String best(Model model, @PageableDefault(size = 100, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        /** 아이템 **/
        Page<Item> items = itemRepository.findAllByBestItem(pageable);
        model.addAttribute("items", items);


        if (!items.isEmpty()) {
            int pageNum = items.getPageable().getPageNumber(); // 현재 페이지
            int totalPages = items.getTotalPages(); // 총 페이지수
            int pageBlock = 5; // 블럭의 수
            int startBlockPage = ((pageNum) / pageBlock) * pageBlock + 1; // 수정된 부분
            int endBlockPage = startBlockPage + pageBlock - 1;
            endBlockPage = Math.min(totalPages, endBlockPage); // 수정된 부분

            model.addAttribute("currentPage", pageNum);
            model.addAttribute("items", items);
            model.addAttribute("startBlockPage", startBlockPage);
            model.addAttribute("endBlockPage", endBlockPage);
            model.addAttribute("totalPages", totalPages); // 추가된 부분
        }
        return "list/best";
    }

    @GetMapping("/sale")
    public String sale(Model model, @PageableDefault(size = 100, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        /** 아이템 **/
        Page<Item> items = itemRepository.findAllBySaleItem(pageable);
        model.addAttribute("items", items);


        if (!items.isEmpty()) {
            int pageNum = items.getPageable().getPageNumber(); // 현재 페이지
            int totalPages = items.getTotalPages(); // 총 페이지수
            int pageBlock = 5; // 블럭의 수
            int startBlockPage = ((pageNum) / pageBlock) * pageBlock + 1; // 수정된 부분
            int endBlockPage = startBlockPage + pageBlock - 1;
            endBlockPage = Math.min(totalPages, endBlockPage); // 수정된 부분

            model.addAttribute("currentPage", pageNum);
            model.addAttribute("items", items);
            model.addAttribute("startBlockPage", startBlockPage);
            model.addAttribute("endBlockPage", endBlockPage);
            model.addAttribute("totalPages", totalPages); // 추가된 부분
        }
        return "list/sale";
    }

}
