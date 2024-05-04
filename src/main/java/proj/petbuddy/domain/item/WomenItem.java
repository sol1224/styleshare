package proj.petbuddy.domain.item;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import proj.petbuddy.domain.category.Category;
import proj.petbuddy.domain.stutus.Display;
import proj.petbuddy.domain.stutus.Gender;
import proj.petbuddy.domain.stutus.SellStatus;
import proj.petbuddy.exception.NotEnoughStockException;

@Entity
@Getter
@Setter
public class WomenItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "WOMENITEM_ID")
    private Long seq;

    @Enumerated(EnumType.STRING)
    private SellStatus sellStatus;
    @Enumerated(EnumType.STRING)
    private Display display;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * 브랜드명
     * 상품명
     * 거래처명
     * 판매가
     * 공급처가
     * 할인율
     * 컬러
     * 사이즈
     * 상세페이지
     * 재고
     */


    private String brand;
    private String name;
    private String vendorName;
    private Integer price;
    private Integer vendorPrice;
    private Integer discount;
    private String color;
    private String size;
    private String detailPage;
    private Integer count;
    private String imgStr;

    @OneToOne
    private ItemImg itemImg;

    @Builder
    public WomenItem(Category category, String brand, String name, String vendorName, Integer price, Integer vendorPrice, Integer discount, String color, String size, String detailPage, SellStatus sellStatus, Display display, String imgStr) {
        this.category = category;
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
    }

    public WomenItem() {
    }


    public WomenItem toDTO(WomenItem dto) {
        dto.setSeq(this.seq);
        dto.setSellStatus(this.sellStatus);
        dto.setDisplay(this.display);
        dto.setGender(this.gender);
//        dto.setCategoryId(this.category.getId());
        dto.setBrand(this.brand);
        dto.setName(this.name);
        dto.setVendorName(this.vendorName);
        dto.setPrice(this.price);
        dto.setVendorPrice(this.vendorPrice);
        dto.setDiscount(this.discount);
        dto.setColor(this.color);
        dto.setSize(this.size);
        dto.setDetailPage(this.detailPage);
        dto.setCount(this.count);
        dto.setImgStr(this.imgStr);

        return dto;
    }


    // ========== 비즈니스 로직 ========== //
    /**
     * 거래 완료/취소 시, count +1, -1
     */
    /**
     * count 증가
     **/
    public void addCount(int count) {
        this.count += count;
    }

    /**
     * count 감소
     **/
    public void adoptionComplete(int count) {
        int restCount = this.count - count;
        if (restCount < 0) {
            throw new NotEnoughStockException("need more restCount");
        }
        this.count = restCount;
    }


}
