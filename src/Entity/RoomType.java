package Entity;

public enum RoomType {
    SINGLE("Single"),
    DOUBLE("Double"),
    QUEEN("Queen"),
    TRIPLE("Triple"),
    QUAD("Quad");

    private String type;

    RoomType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
