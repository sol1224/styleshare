package proj.petbuddy.domain.map;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "map")
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "map_id")
    private Long id;

    private String name;
    private String keyword;
    private String type;
    private String address1;
    private String address2;
    private String tel;
    private Integer review;
    private String detailaddress;
    private String xcoord;
    private String ycoord;


}
