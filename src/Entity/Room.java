package Entity;

public class Room {
    private String id;
    private RoomType roomType;
    private double price_per_hour;

    public Room(){;}

    public Room(String id, RoomType roomType, double price_per_hour) {
        this.id = id;
        this.roomType = roomType;
        this.price_per_hour = price_per_hour;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice_per_hour() {
        return price_per_hour;
    }

    public void setPrice_per_hour(double price_per_hour) {
        this.price_per_hour = price_per_hour;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", roomType='" + roomType + '\'' +
                ", price_per_hour=" + price_per_hour +
                '}';
    }
}
