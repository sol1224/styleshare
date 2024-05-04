package proj.petbuddy.domain.stutus;

public enum Display {
    DISPLAY("진열함"), NOTDISPLAY("진열안함");

    private final String description;

    Display(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
