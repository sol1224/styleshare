package proj.petbuddy.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import proj.petbuddy.domain.mypage.EventReceipt;
import proj.petbuddy.domain.mypage.Member;
import proj.petbuddy.domain.mypage.PersonalInformation;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDTO {

    private String phoneNumber;
    private String email;
    private EventReceipt eventReceipt;
    private PersonalInformation personalInformation;

    /**
     * Dto -> Entity
     */
    public Member toEntity() {
        Member member = Member.builder()
                .phoneNumber(phoneNumber)
                .email(email)
                .eventReceipt(eventReceipt)
                .personalInformation(personalInformation)
                .build();

        return member;
    }
}
