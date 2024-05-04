package proj.petbuddy.domain.mypage;

public enum PersonalInformation {
    AGREE("동의"), DISAGREE("비동의");

    private final String description;

    PersonalInformation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
