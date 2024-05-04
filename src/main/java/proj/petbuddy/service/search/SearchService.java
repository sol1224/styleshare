package proj.petbuddy.service.search;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.petbuddy.domain.item.Item;
import proj.petbuddy.domain.item.MenItem;
import proj.petbuddy.domain.item.WomenItem;
import proj.petbuddy.repository.item.ItemRepository;
import proj.petbuddy.repository.item.MenItemRepository;
import proj.petbuddy.repository.item.WomenItemRepository;

@RequiredArgsConstructor
@Transactional
@Service
public class SearchService {

    private final WomenItemRepository womenItemRepository;
    private final MenItemRepository menItemRepository;
    private final ItemRepository itemRepository;


    /**
     * 검색 서비스 (여성)
     **/
    @Transactional
    public Page<Item> search(String keyword, Pageable pageable) {
        Page<Item> list = itemRepository.findByBrandOrName(keyword, pageable);
        return list;
    }

    @Transactional
    public Page<Item> manSearch(String keyword, Pageable pageable) {
        Page<Item> menList = itemRepository.findByBrandOrName(keyword, pageable);
        return menList;
    }
}
