package proj.petbuddy.domain.stutus;

public enum SellStatus {
    SELL("판매중"), NOTSELL("판매안함");

    private final String description;

    SellStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
