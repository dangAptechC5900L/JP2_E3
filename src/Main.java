import Entity.*;
import Services.BookingService;
import Services.RoomService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String cusName, cusPhone,rmType;
        LocalDateTime checkIn,checkOut;
        List<Room> rooms= new ArrayList<Room>();
        List<Customer> customers= new ArrayList<Customer>();
        List<Booking> bookings=new ArrayList<Booking>();

        rooms.add(new Room("RS001",RoomType.SINGLE,8));
        rooms.add(new Room("RD001",RoomType.DOUBLE,12));
        rooms.add(new Room("RQ002",RoomType.QUEEN,35));
        rooms.add(new Room("RT001",RoomType.TRIPLE,12.5));
        rooms.add(new Room("RQ001",RoomType.QUAD,20.5));

        customers.add(new Customer("001","Mr.Linus Tovaldo","84125325346457"));
        customers.add(new Customer("002","Mr.Bill","91124235346467"));
        customers.add(new Customer("003","Mr.Turing","911423534646"));

        bookings.add(new Booking(1, rooms.get(0), customers.get(0), LocalDateTime.of(2023, 3, 15, 9, 30, 15), LocalDateTime.of(2023,3,16,12,30,45)));
        bookings.add(new Booking(2, rooms.get(0), customers.get(1), LocalDateTime.of(2023,6,9,19,30,25),LocalDateTime.of(2023,6,10,11,25,15)));
        bookings.add(new Booking(3, rooms.get(1), customers.get(1), LocalDateTime.of(2023,3,11, 10,10,5),LocalDateTime.of(2023,03,13,11,5,10)));
        bookings.add(new Booking(4, rooms.get(3), customers.get(2), LocalDateTime.of(2023,11,11,11,11,15),LocalDateTime.of(2023,11,13,11,15,15)));
        bookings.add(new Booking(5, rooms.get(3), customers.get(0), LocalDateTime.of(2023,10,25,9,20,25),LocalDateTime.of(2023,10,26,12,25,30)));
        bookings.add(new Booking(6, rooms.get(4), customers.get(0), LocalDateTime.of(2023,8,18,12,25,35),LocalDateTime.of(2023,8,19,11,35,20)));

//        2023-07-15T15:30
//        2024-09-12 23:23
//        91124235346467

        BookingService bookingService = new BookingService(rooms, customers, bookings);
        RoomService roomService=new RoomService();
        Scanner sc=new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("===== Room Booking System Menu =====");
            System.out.println("1. Book a Room");
            System.out.println("2. Display Booking Information by cus_name, cus_phone, or room_id");
            System.out.println("3. Revenue Statistics by Room Type");
            System.out.println("4. Display Room Type with Highest Revenue in 2023");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        System.out.print("Enter customer name:");
                        cusName = sc.nextLine();
                        System.out.print("Enter customer phone:");
                        cusPhone = sc.nextLine();
                        System.out.print("Enter room type (SINGLE, DOUBLE, QUEEN, TRIPLE, QUAD):");
                        RoomType roomType = RoomType.valueOf(sc.next().toUpperCase());

                        // Nhập check-in date-time
                        System.out.print("Enter check-in date and time (yyyy-MM-dd HH:mm):");
                        checkIn = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                        // Nhập check-out date-time
                        System.out.print("Enter check-out date and time (yyyy-MM-dd HH:mm):");
                        checkOut = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                        Optional<Booking> bookingOptional = bookingService.bookRoom(cusName, cusPhone, roomType, checkIn, checkOut);
                        if (bookingOptional.isPresent()) {
//                            Booking booking = bookingOptional.get();
//                            bookingService.save(booking);
                          bookingService.bookings=bookings;
                            System.out.println("Room booked successfully! Booking details: " + bookingOptional.get());
                            System.out.println(bookings);
                        } else {
                            System.out.println("Room booking failed.");
                        }
                        break;


                    case 2:
                        System.out.println("Enter customer name:");
                        cusName = sc.nextLine();
                        System.out.println("Enter customer phone:");
                        cusPhone = sc.nextLine();
                        System.out.println("Enter room ID:");
                        String roomId = sc.nextLine();
                        Map<Customer,Booking> bookingMap = bookingService.getBookingInformation(cusName, cusPhone, roomId);
                        if (bookings.isEmpty()) {
                            System.out.println("No bookings found.");
                        } else {
                            for(Map.Entry<Customer,Booking> b : bookingMap.entrySet()){
                                System.out.println(b.getKey()+"=" + b.getValue());
                            }
                        }
                        break;
                    case 3:
                        Map<RoomType, Double> roomTypes = bookingService.getRevenueStatisticsByRoomType();
                        roomTypes.forEach((type, revenue) -> {
                            System.out.println(type + ": " + revenue + " $");
                        });
                        break;
                    case 4:
                        System.out.println("Nhập ID Room cần tìm kiếm: ");
                        rmType= sc.nextLine();
                           roomService.findById(rmType);
                        break;
                    case 5:
                        System.out.println("Exiting the application...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number from 1 to 5.");
                }
            } catch (Exception e) {
                System.out.println("Bi Loi roi.");
                sc.nextLine(); // Clear the input buffer
            }
        } while (choice != 5);

             sc.close();
    }
}
