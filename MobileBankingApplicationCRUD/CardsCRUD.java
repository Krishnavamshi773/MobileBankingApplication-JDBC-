package MobileBankingApplicationCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import MobileBankingApplicationException.InvalidCardNumberException;

public class CardsCRUD {

    // Insert a new card record
    public void insertCard(Connection con, Scanner scanner) {
        String insertSQL = "INSERT INTO Cards (Card_Number, Holder_Name, Linked_Upi_id, Card_Type, Expiry_Date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstatement = con.prepareStatement(insertSQL)) {
            System.out.print("Enter Card Number (16 digits): ");
            String cardNumber = scanner.next();

            // Validate card number length
            if (cardNumber.length() != 16) {
                throw new InvalidCardNumberException("Card number must be exactly 16 digits.");
            }
            pstatement.setString(1, cardNumber);

            System.out.print("Enter Holder Name: ");
            pstatement.setString(2, scanner.next());

            System.out.print("Enter Linked UPI ID: ");
            pstatement.setString(3, scanner.next());

            System.out.println("Select Card Type:");
            System.out.println("1. Credit");
            System.out.println("2. Debit");
            int typeChoice = scanner.nextInt();
            String cardType = switch (typeChoice) {
                case 1 -> "Credit";
                case 2 -> "Debit";
                default -> {
                    System.out.println("Invalid option.");
                    yield null; // exit the switch
                }
            };
            pstatement.setString(4, cardType);

            System.out.print("Enter Expiry Date (YYYY-MM-DD): ");
            pstatement.setDate(5, java.sql.Date.valueOf(scanner.next()));

            int rowsInserted = pstatement.executeUpdate();
            System.out.println("Record inserted successfully. Rows affected: " + rowsInserted);

        } catch (SQLException e) {
            System.out.println("Error inserting card: " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidCardNumberException e) {
            System.out.println("Invalid Card Number: " + e.getMessage());
        }
    }
   // Update an existing card record
    public void updateCard(Connection con, Scanner scanner) {
        System.out.print("Enter Card Number to update: ");
        String cardNumber = scanner.next();

        System.out.print("Enter new Holder Name: ");
        String holderName = scanner.next();

        System.out.print("Enter new Expiry Date (YYYY-MM-DD): ");
        String expiryDate = scanner.next();

        String updateSQL = "UPDATE Cards SET Holder_Name = ?, Expiry_Date = ? WHERE Card_Number = ?";
        try (PreparedStatement pstatement = con.prepareStatement(updateSQL)) {
            pstatement.setString(1, holderName);
            pstatement.setDate(2, java.sql.Date.valueOf(expiryDate));
            pstatement.setString(3, cardNumber);

            int rowsUpdated = pstatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Record updated successfully. Rows affected: " + rowsUpdated);
            } else {
                System.out.println("No record found with Card Number: " + cardNumber);
            }
        } catch (SQLException e) {
            System.out.println("Error updating card: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Delete a card record
    public void deleteCard(Connection con, Scanner scanner) {
        System.out.print("Enter Card Number to delete: ");
        String cardNumber = scanner.next();

        String deleteSQL = "DELETE FROM Cards WHERE Card_Number = ?";
        try (PreparedStatement pstatement = con.prepareStatement(deleteSQL)) {
            pstatement.setString(1, cardNumber);

            int rowsDeleted = pstatement.executeUpdate();
            System.out.println("Record deleted successfully. Rows affected: " + rowsDeleted);
        } catch (SQLException e) {
            System.out.println("Error deleting card: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Select and display all card records
    public void getAllCards(Connection con) {
        String selectSQL = "SELECT * FROM Cards";
        try (PreparedStatement pstatement = con.prepareStatement(selectSQL);
             ResultSet resultSet = pstatement.executeQuery()) {

            System.out.println("--------------------------------------------------------");
            System.out.println("Card Number | Holder Name          | Linked UPI ID | Card Type | Expiry Date");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("Card_Number") + " | " +
                        resultSet.getString("Holder_Name") + " | " +
                        resultSet.getString("Linked_Upi_id") + " | " +
                        resultSet.getString("Card_Type") + " | " +
                        resultSet.getDate("Expiry_Date"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching cards: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
