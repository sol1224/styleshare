package proj.petbuddy.service.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import proj.petbuddy.domain.category.Category;
import proj.petbuddy.domain.item.Item;
import proj.petbuddy.domain.item.ItemImg;
import proj.petbuddy.dto.category.CategoryDto;
import proj.petbuddy.dto.item.ItemDetailResponse;
import proj.petbuddy.dto.item.ItemFormDTO;
import proj.petbuddy.dto.item.ItemResponseDTO;
import proj.petbuddy.repository.board.CategoryRepository;
import proj.petbuddy.repository.item.ItemImgRepository;
import proj.petbuddy.repository.item.ItemRepository;
import proj.petbuddy.repository.item.WomenItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;
    private final CategoryRepository categoryRepository;


    /**
     * 신규 등록
     */
    public Long saveItem(ItemFormDTO itemFormDto, List<MultipartFile> itemImgFileList, Long selectedValue) throws Exception {
        // 상품 등록
        Item item = itemFormDto.toEntity(itemFormDto);
        System.out.println("selectedValue_" + selectedValue);

        // ItemFormDTO에서 Category 객체를 가져와서 설정
        Category category = new Category();
        category.setId(selectedValue);
        item.setCategory(category);

        itemRepository.save(item);



        // 이미지 등록
        for (int i = 0, max = itemImgFileList.size(); i < max; i++) {
            ItemImg itemImg = ItemImg.builder()
                    .item(item)
//                    .repimgYn(i == 0 ? "Y" : "N")
                    .build();

            /**
             *****
             * *****  ItemImg 주소, Item에도 저장 -> 테스트 상품 1500개(남성/여성/라이프) 와 새로등록 new 상품과
             * 같이 repository 에서 추출하기 위함 *********
             * **/
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
            item.setImgStr(itemImg.getImgStr());
        }

        return item.getId();
    }

    public ItemResponseDTO findById(Long id) {
        ItemImg itemImg = itemImgRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        return new ItemResponseDTO(itemImg);
    }


    /**
     * 품종 검색
     */
    @Transactional
    public List<CategoryDto> getCategoryList() {
        List<CategoryDto> results = categoryRepository.findAll().stream().map(CategoryDto::of).collect(Collectors.toList());
        return results;
    }


    @Transactional
    /** 상품상세 **/
    public ItemDetailResponse itemDetail(Long id) {
        Item item = itemRepository.findById(id).get();
        // 해당 seq 가 존재할 경우
        return new ItemDetailResponse(item);
    }
}

