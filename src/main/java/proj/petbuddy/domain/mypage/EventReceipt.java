package proj.petbuddy.domain.mypage;

public enum EventReceipt {
    RECEIVE("수신"), NONRECEIVE("비수신");

    private final String description;

    EventReceipt(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
