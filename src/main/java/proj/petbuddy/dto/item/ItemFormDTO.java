package proj.petbuddy.dto.item;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Event;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import proj.petbuddy.domain.category.Category;
import proj.petbuddy.domain.item.Item;
import proj.petbuddy.domain.item.ItemImg;
import proj.petbuddy.domain.item.WomenItem;
import proj.petbuddy.domain.mypage.EventReceipt;
import proj.petbuddy.domain.mypage.PersonalInformation;
import proj.petbuddy.domain.stutus.Display;
import proj.petbuddy.domain.stutus.Gender;
import proj.petbuddy.domain.stutus.SellStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ItemFormDTO {

    private Long seq;

    @Enumerated(EnumType.STRING)
    private SellStatus sellStatus;
    @Enumerated(EnumType.STRING)
    private Display display;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank(message = "브랜드는 필수 입력 값입니다.")
    private String brand;

    @NotBlank(message = "상품명 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "공급처명 필수 입력 값입니다.")
    private String vendorName;

    @NotNull(message = "판매가 필수 입력 값입니다.")
    private Integer price;

    @NotNull(message = "공급처가는 필수 입력 값입니다.")
    private Integer vendorPrice;

    private Integer discount;

    private String color;
    private String size;
    private String detailPage;
    private ItemImg itemImg;

    @Value("999")
    @Min(1)
    private Integer count;
    private String imgStr;

    private Long cateId;

    private EventReceipt eventReceipt;
    private PersonalInformation personalInformation;

    private List<ItemImgDTO> itemImgDtoList = new ArrayList<>();
    private List<Long> itemImgIds = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();

    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToMany(mappedBy = "item")
    private List<Category> categories = new ArrayList<>();


    public Item create() {
        return modelMapper.map(this, Item.class);
    }

    @Builder
    public ItemFormDTO(String brand, String name, String vendorName, Integer price, Integer vendorPrice, Integer discount, String color, String size, String detailPage, SellStatus sellStatus, Display display, String imgStr, ItemImg itemImg) {
        this.brand = brand;
        this.name = name;
        this.vendorName = vendorName;
        this.price = price;
        this.vendorPrice = vendorPrice;
        this.discount = discount;
        this.color = color;
        this.size = size;
        this.detailPage = detailPage;
        this.sellStatus = sellStatus;
        this.display = display;
        this.imgStr = imgStr;
        this.itemImg = itemImg;
    }

    public Item toEntity(ItemFormDTO dto) {
        Item entity = Item.builder()
                .brand(dto.brand)
                .name(dto.name)
                .vendorName(dto.vendorName)
                .price(dto.price)
                .vendorPrice(dto.vendorPrice)
                .discount(dto.discount)
                .color(dto.color)
                .size(dto.size)
                .detailPage(dto.detailPage)
                .sellStatus(dto.sellStatus)
                .display(dto.display)
                .imgStr(dto.imgStr)
                .itemImg(dto.itemImg)
                .createdAt(dto.createdAt)
                .build();
        return entity;
    }


}

