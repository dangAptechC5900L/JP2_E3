package Services;

import Entity.Booking;
import Entity.Customer;
import Entity.Room;
import Entity.RoomType;
import Generic.IGeneric;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BookingService implements IGeneric<Booking> {
    public  List<Room> rooms;
    public  List<Customer> customers;
    public  List<Booking> bookings;
    private RoomService roomService;

    public BookingService(){;}

    public BookingService(List<Room> rooms,List<Customer> customers,List<Booking> bookings){
        this.rooms=rooms;
        this.customers=customers;
        this.bookings=bookings;
    }

    @Override
    public Booking save(Booking booking) {
        // Ví dụ sử dụng phương thức findById từ RoomService
//        Room room = roomService.findById(booking.getRoom().getId());
//        if (room == null) {
//            System.out.println("Room not found.");
//            return null;
//        }
        bookings.add(booking);
        System.out.println("Booking saved successfully!");
        return booking;
    }

    @Override
    public Booking delete(Booking booking) {
        return null;
    }

    @Override
    public Booking findById(String id) {
        return null;
    }


    //3.1
    public Optional<Booking> bookRoom(String cus_name, String cusPhone, RoomType roomType, LocalDateTime checkIn, LocalDateTime checkOut){
        Optional<Customer> customerOp=customers.stream()
                .filter(c->c.getCus_name().equals(cus_name) && c.getCus_phone().equals(cusPhone))
                .findFirst();

        if (customerOp.isEmpty()) {
            System.out.println("Customer not found.");
            return Optional.empty();
        }

        Optional<Room> roomOp = rooms.stream()
                .filter(r -> r.getRoomType() == roomType)
                .findFirst();

        if (roomOp.isEmpty()) {
            System.out.println("Room type not available.");
            return Optional.empty();
        }


        Customer customer = customerOp.get();
        Room room = roomOp.get();
        int bookingId = bookings.size() + 1;
        Booking booking = new Booking(bookingId, room, customer, checkIn, checkOut);
        bookings.add(booking);
        System.out.println("Room booked successfully!");
        return Optional.of(booking);
    }

    // 3.2 Display booking information by customer name, phone, or room id
//    public List<Booking> getBookingInformation(String cusName, String cusPhone, String roomId) {
//        return bookings.stream()
//                .filter(b -> (b.getCus_id().getCus_name().equals(cusName) && b.getCus_id().getCus_phone().equals(cusPhone) && b.getRoom_id().getId().equals(roomId)))
//                .collect(Collectors.toList());
//    }

    public Map<Customer,Booking> getBookingInformation(String cusName, String cusPhone, String roomId){
        Map<Customer,Booking> bookingMap=bookings.stream()
                 .filter(b -> (b.getCustomer().getCus_name().equals(cusName) && b.getCustomer().getCus_phone().equals(cusPhone) && b.getRoom().getId().equals(roomId)))
                .collect(Collectors.toMap(Booking::getCustomer, Function.identity()));
                return bookingMap;
    }

    //3.3
//    public Map<RoomType,Double> getRevenueStatisticsByRoomType(String roomType){
//        Map<Room,Booking> bookingMap= new HashMap<>();
//        rooms.stream().forEach(room -> System.out.println(room));
////        for (Room r:)
////                bookings.stream()
////                .filter(r->r.getRoom().getRoomType().equals(roomType))
////                .collect(Collectors.toMap(Booking::getRoom,Function.identity()));
////        return bookingMap;
//
//    }

    public Map<RoomType,Double> getRevenueStatisticsByRoomType(){
        return bookings.stream()
                .collect(Collectors.groupingBy(
                        b->b.getRoom().getRoomType(),
                        Collectors.summingDouble(b->{
                            Duration duration=Duration.between(b.getCheck_in_datetime(),b.getCheck_out_datetime());
                            double hours=duration.toHours()+(double) duration.toMinutesPart()/60;
                            return  Math.ceil(hours) * b.getRoom().getPrice_per_hour();
                        })
                ));
    }

    public Map<RoomType, Double> getRevenueStatisticsByRoomType1() {
        return rooms.stream()
                .collect(Collectors.toMap(
                        Room::getRoomType,
                        room -> bookings.stream()
                                .filter(booking -> booking.getRoom().getId().equals(room.getId()))
                                .mapToDouble(booking -> {
                                    long hours = Duration.between(booking.getCheck_in_datetime(), booking.getCheck_out_datetime()).toHours();
                                    double roundedHours = Math.ceil(hours);
                                    return roundedHours * room.getPrice_per_hour();
                                })
                                .sum(),
                        Double::sum
                ));
    }


    //3.4




}
