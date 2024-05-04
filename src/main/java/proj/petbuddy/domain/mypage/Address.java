package proj.petbuddy.domain.mypage;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Embeddable
@Getter
@RequiredArgsConstructor
@Data
public class Address {

    private String address;
    private String detailAddress;


    public Address(String address, String detailAddress) {
        this.address = address;
        this.detailAddress = detailAddress;
    }
}
