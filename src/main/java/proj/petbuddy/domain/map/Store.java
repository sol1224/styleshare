package proj.petbuddy.domain.map;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "store")
@RequiredArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "store_id")
    private Long id;

    private String name;
    private String address;
    private String tel;
    //    private String operatingHours; //운영시간
    private String country;
    private String xCoord;
    private String yCoord;
    private String zipCode;
    private String time;
    private String time2;


    @Builder
    public Store(String name, String address, String tel, String country, String xCoord, String yCoord, String zipCode, String time, String time2) {
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.country = country;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zipCode = zipCode;
        this.time = time;
        this.time2 = time2;
    }
}

