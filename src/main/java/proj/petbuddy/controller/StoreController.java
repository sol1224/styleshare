package proj.petbuddy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import proj.petbuddy.domain.map.Store;
import proj.petbuddy.repository.StoreRepository;
import proj.petbuddy.service.store.StoreService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public class StoreController {

    private final StoreRepository storeRepository;
    private final StoreService storeService;


    @GetMapping("/find-store")
    public String findStore2(Model model) {
        List<Store> storeList = storeRepository.findAll();
        model.addAttribute("storeList", storeList);
        return "store/storeMap2";
    }

}
