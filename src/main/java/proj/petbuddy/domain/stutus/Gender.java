package proj.petbuddy.domain.stutus;

public enum Gender {
    MALE("남성"), FEMALE("여성");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
