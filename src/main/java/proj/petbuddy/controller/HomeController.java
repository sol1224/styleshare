package proj.petbuddy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import proj.petbuddy.domain.cart.Cart;
import proj.petbuddy.domain.cart.CartItem;
import proj.petbuddy.domain.item.Item;
import proj.petbuddy.repository.cart.CartItemRepository;
import proj.petbuddy.repository.cart.CartRepository;
import proj.petbuddy.repository.item.ItemImgRepository;
import proj.petbuddy.repository.item.ItemRepository;
import proj.petbuddy.repository.item.MenItemRepository;
import proj.petbuddy.repository.item.WomenItemRepository;
import proj.petbuddy.service.item.ItemService;
import proj.petbuddy.service.search.SearchService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final ItemRepository itemRepository;

    private final CartItemRepository cartItemRepository;
    /**
     * 메인 페이지
     **/
    @GetMapping("/")
    public String main(Model model) {
        List<Item> item = itemRepository.findAll();
        model.addAttribute("item", item);
        return "main/mainList";
    }


    @GetMapping("/intro")
    public String introStyleShare() {
        return "main/introStyleShare";

    }


}
