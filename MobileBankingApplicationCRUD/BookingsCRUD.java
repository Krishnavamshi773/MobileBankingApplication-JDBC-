package MobileBankingApplicationCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;
public class BookingsCRUD {
	
    // Insert a new booking record
    public void insertBooking(Connection con, Scanner scanner) {
        String insertSQL = "INSERT INTO Bookings (Booking_id, Booking_Type, Amount, Paid_From_Upi_id, Booking_Date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstatement = con.prepareStatement(insertSQL)) {
            // Generate a 12-digit random Booking_id
            String bookingId = BookingIdGenerator.generateBookingId();
            pstatement.setString(1, bookingId);
            // Prompt for Booking_Type
            System.out.println("Select Booking Type:");
            System.out.println("1. Movie Ticket");
            System.out.println("2. Bus Ticket");
            System.out.println("3. Train Ticket");
            System.out.println("4. Flight Ticket");
            System.out.println("5. Hotels");
            int typeChoice = scanner.nextInt();
            String bookingType = switch (typeChoice) {
                case 1 -> "Movie Ticket";
                case 2 -> "Bus Ticket";
                case 3 -> "Train Ticket";
                case 4 -> "Flight Ticket";
                case 5 -> "Hotels";
                default -> {
                    System.out.println("Invalid option. Defaulting to 'Movie Ticket'");
                    yield "Movie Ticket";
                }
            };
            pstatement.setString(2, bookingType);

            // Prompt for Amount
            System.out.print("Enter Booking Amount: ");
            double amount = scanner.nextDouble();
            pstatement.setDouble(3, amount);

            // Prompt for Paid_From_Upi_id
            System.out.print("Enter Paid From UPI ID: ");
            String upiId = scanner.next();
            pstatement.setString(4, upiId);

            // Set the current timestamp for Booking_Date
            pstatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            int rowsInserted = pstatement.executeUpdate();
            System.out.println("Booking record inserted successfully. Rows affected: " + rowsInserted);
        } catch (SQLException e) {
            System.out.println("Error inserting booking: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Update an existing booking record
    public void updateBooking(Connection con, Scanner scanner) {
        System.out.print("Enter Booking ID to update: ");
        String bookingId = scanner.next();

        System.out.print("Enter new Amount: ");
        double amount = scanner.nextDouble();

        String updateSQL = "UPDATE Bookings SET Amount = ? WHERE Booking_id = ?";
        try (PreparedStatement pstatement = con.prepareStatement(updateSQL)) {
            pstatement.setDouble(1, amount);
            pstatement.setString(2, bookingId);

            int rowsUpdated = pstatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Booking record updated successfully. Rows affected: " + rowsUpdated);
            } else {
                System.out.println("No record found with Booking ID: " + bookingId);
            }
        } catch (SQLException e) {
            System.out.println("Error updating booking: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Delete a booking record
    public void deleteBooking(Connection con, Scanner scanner) {
        System.out.print("Enter Booking ID to cancel: ");
        String bookingId = scanner.next();

        String deleteSQL = "DELETE FROM Bookings WHERE Booking_id = ?";
        try (PreparedStatement pstatement = con.prepareStatement(deleteSQL)) {
            pstatement.setString(1, bookingId);

            int rowsDeleted = pstatement.executeUpdate();
            System.out.println("Booking cancelled successfully. Rows affected: " + rowsDeleted);
        } catch (SQLException e) {
            System.out.println("Error cancelling booking: " + e.getMessage());
            e.printStackTrace();
        }
    }
    // Retrieve and display all booking records
    public void getAllBookings(Connection con) {
        String selectSQL = "SELECT * FROM Bookings";
        try (PreparedStatement pstatement = con.prepareStatement(selectSQL);
             ResultSet resultSet = pstatement.executeQuery()) {

            System.out.println("---------------------------------------------------------------");
            System.out.println("Booking ID | Booking Type       | UPI ID | Amount | Booking Date");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("Booking_id") + " | " +
                        resultSet.getString("Booking_Type") + " | " +
                        resultSet.getString("Paid_From_Upi_id") + " | " +
                        resultSet.getDouble("Amount") + " | " +
                        resultSet.getTimestamp("Booking_Date"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching bookings: " + e.getMessage());
            e.printStackTrace();
        }
    }
}