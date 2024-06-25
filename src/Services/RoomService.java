package Services;

import Entity.Booking;
import Entity.Customer;
import Entity.Room;
import Generic.IGeneric;

import java.util.List;
import java.util.stream.Collectors;

public class RoomService implements IGeneric<Room> {

    public  List<Room> rooms;
    public  List<Customer> customers;
    public  List<Booking> bookings;

    public RoomService() {;}

    public RoomService(List<Room> rooms, List<Customer> customers, List<Booking> bookings) {
        this.rooms = rooms;
        this.customers = customers;
        this.bookings = bookings;
    }

    @Override
    public Room save(Room room) {
        return null;
    }

    @Override
    public Room delete(Room room) {
        System.out.println("....");
        return null;
    }

    @Override
    public Room findById(String id) {
        return this.rooms.stream()
                .filter(r->r.getId().equals(id))
                .findFirst().get();
    }
}
