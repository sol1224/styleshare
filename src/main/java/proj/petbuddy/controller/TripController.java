package proj.petbuddy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import proj.petbuddy.domain.map.Map;
import proj.petbuddy.repository.map.MapRepository;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public class TripController {

    private final MapRepository mapRepository;

    @GetMapping("/trip")
    public String trip(Model model) {
        List<Map> mapList = mapRepository.findAll();
        model.addAttribute("mapList", mapList);
        return "trip/tripMap";
    }
}