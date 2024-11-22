package MobileBankingApplicationCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;

public class MoneyTransferCRUD {

    // Insert a new money transfer record
    private final AccountsCRUD accountsCRUD = new AccountsCRUD();  // Use AccountsCRUD instance for balance updates

    // Insert a new money transfer record
    public void insertTransfer(Connection con, Scanner scanner) {
        String insertSQL = "INSERT INTO Money_Transfer (Transfer_id, Transfer_Type, To_Upi_Id, To_Mobile_No, To_Bank_Account, Amount, From_Upi_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstatement = con.prepareStatement(insertSQL)) {
            // Generate a unique Transfer_id
            String transferId = String.format("%010d", (long) (Math.random() * 1_000_000_0000L));
            pstatement.setString(1, transferId);

            // Select transfer type and capture relevant details
            System.out.println("Select Transfer Type:\n1. To UPI ID\n2. To Mobile Number\n3. To Bank Account");
            int typeChoice = scanner.nextInt();
            String transferType;
            String toUpiId = null;
            String toMobileNo = null;
            String toBankAccount = null;

            switch (typeChoice) {
                case 1 -> {
                    transferType = "To_Upi_Id";
                    System.out.print("Enter Recipient UPI ID: ");
                    toUpiId = scanner.next();
                }
                case 2 -> {
                    transferType = "To_Mobile_No";
                    System.out.print("Enter Mobile Number: ");
                    toMobileNo = scanner.next();
                }
                case 3 -> {
                    transferType = "To_Bank_Account";
                    System.out.print("Enter Bank Account Number: ");
                    toBankAccount = scanner.next();
                }
                default -> {
                    System.out.println("Invalid option.");
                    return;
                }
            }

            pstatement.setString(2, transferType);
            pstatement.setString(3, toUpiId);
            pstatement.setString(4, toMobileNo);
            pstatement.setString(5, toBankAccount);

            System.out.print("Enter Amount: ");
            double amount = scanner.nextDouble();
            pstatement.setDouble(6, amount);

            System.out.print("Enter Sender UPI ID: ");
            String fromUpiId = scanner.next();
            pstatement.setString(7, fromUpiId);

            // Perform the transfer
            con.setAutoCommit(false);  // Start transaction

            // Debit from sender's account
            if (!accountsCRUD.debitBalance(con, fromUpiId, amount)) {
                System.out.println("Insufficient balance.");
                con.rollback();
                return;
            }

            // Credit to recipient's account if transferring to UPI ID
            if ("To_Upi_Id".equals(transferType) && !accountsCRUD.creditBalance(con, toUpiId, amount)) {
                System.out.println("Recipient UPI ID not found.");
                con.rollback();
                return;
            }

            // Insert transfer record
            int rowsInserted = pstatement.executeUpdate();
            con.commit();  // Commit transaction
            System.out.println("Transfer completed successfully. Rows affected: " + rowsInserted);

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
    }
	    // Method to update the user's balance
	 /*   private boolean updateUserBalance(Connection con, String upiId, double amount) throws SQLException {
	        String updateBalanceSQL = "UPDATE Users SET Balance = Balance + ? WHERE Upi_id = ?";
	        try (PreparedStatement pstatement = con.prepareStatement(updateBalanceSQL)) {
	            pstatement.setDouble(1, amount);
	            pstatement.setString(2, upiId);
	            return pstatement.executeUpdate() > 0;
	        }
	    }
	
    // Update an existing money transfer record
	public void updateTransfer(Connection con, Scanner scanner) {
	    System.out.print("Enter Transfer ID to update: ");
	    String transferId = scanner.next();

	    System.out.print("Enter new Amount: ");
	    double amount = scanner.nextDouble();

	    String updateSQL = "UPDATE Money_Transfer SET Amount = ? WHERE Transfer_id = ?";
	    try (PreparedStatement pstatement = con.prepareStatement(updateSQL)) {
	        pstatement.setDouble(1, amount);      // Set the new Amount
	        pstatement.setString(2, transferId);  // Set the Transfer ID to identify the record

	        int rowsUpdated = pstatement.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Record updated successfully. Rows affected: " + rowsUpdated);
	        } else {
	            System.out.println("No record found with Transfer ID: " + transferId);
	        }

	    } catch (SQLException e) {
	        System.out.println("Error updating transfer: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

    // Delete a money transfer record
    public void deleteTransfer(Connection con, Scanner scanner) {
        System.out.print("Enter Transfer ID to delete: ");
        String transferId = scanner.next();

        String deleteSQL = "DELETE FROM Money_Transfer WHERE Transfer_id = ?";
        try (PreparedStatement pstatement = con.prepareStatement(deleteSQL)) {
            pstatement.setString(1, transferId);

            int rowsDeleted = pstatement.executeUpdate();
            System.out.println("Record deleted successfully. Rows affected: " + rowsDeleted);

        } catch (SQLException e) {
            System.out.println("Error deleting transfer: " + e.getMessage());
            e.printStackTrace();
        }
    }
*/
    // Select and display all money transfer records
    public void getAllTransfers(Connection con) {
        String selectSQL = "SELECT * FROM Money_Transfer";
        try (PreparedStatement pstatement = con.prepareStatement(selectSQL);
             ResultSet resultSet = pstatement.executeQuery()) {

            System.out.println("--------------------------------------------------------");
            System.out.println("Transfer ID | Transfer Type | Amount | From UPI ID | Transfer Date");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("Transfer_id") + " | " +
                        resultSet.getString("Transfer_Type") + " | " +
                        resultSet.getDouble("Amount") + " | " +
                        resultSet.getString("From_Upi_id") + " | " +
                        resultSet.getTimestamp("Transfer_Date"));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching transfers: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
