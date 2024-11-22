package MobileBankingApplicationEntities;

import MobileBankingApplicationCRUD.BookingsCRUD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Bookings {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobilebank", "root", "Vamshi123");
        BookingsCRUD bookingsCRUD = new BookingsCRUD();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select Your Choice:\n1. Make Booking\n2. Update Booking\n3. Delete Booking\n4. Get All Bookings\n5. Exit");
            System.out.print("Enter your Option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> bookingsCRUD.insertBooking(connection, scanner);
                case 2 -> bookingsCRUD.updateBooking(connection, scanner);
                case 3 -> bookingsCRUD.deleteBooking(connection, scanner);
                case 4 -> bookingsCRUD.getAllBookings(connection);
                case 5 -> {
                    System.out.println("Thank you for using the Booking management app. Goodbye!");
                    connection.close();
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
