package MobileBankingApplicationCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BillsCRUD {

    // Insert a new bill record
	public void insertBill(Connection con, Scanner scanner) {
	    String insertSQL = "INSERT INTO Bills (Bill_id, Type, Amount, Pay_From_Upi_id) VALUES (?, ?, ?, ?)";
	    String updateBalanceSQL = "UPDATE Accounts SET Balance = Balance - ? WHERE Upi_id = ?";
	    String checkBalanceSQL = "SELECT Balance FROM Accounts WHERE Upi_id = ?";

	    try (PreparedStatement pstatement = con.prepareStatement(insertSQL)) {
	        // Generate a 10-digit numeric Bill ID
	        String billId = String.format("%010d", (long) (Math.random() * 1_000_000_0000L));
	        pstatement.setString(1, billId);

	        System.out.println("Select Bill Type:");
	        System.out.println("1. Mobile Recharge");
	        System.out.println("2. Electricity");
	        System.out.println("3. Loan Repayment");
	        System.out.println("4. DTH Recharge");
	        System.out.println("5. Fast Tag");
	        int typeChoice = scanner.nextInt();
	        String billType = switch (typeChoice) {
	            case 1 -> "Mobile Recharge";
	            case 2 -> "Electricity";
	            case 3 -> "Loan Repayment";
	            case 4 -> "DTH Recharge";
	            case 5 -> "Fast Tag";
	            default -> {
	                System.out.println("Invalid option.");
	                yield null; // exit the switch
	            }
	        };
	        pstatement.setString(2, billType);

	        System.out.print("Enter Amount: ");
	        double amount = scanner.nextDouble();
	        pstatement.setDouble(3, amount);

	        System.out.print("Enter Pay From UPI ID: ");
	        String upiId = scanner.next();
	        pstatement.setString(4, upiId);

	        // Check if sufficient balance exists for the payment
	        try (PreparedStatement balanceCheckStmt = con.prepareStatement(checkBalanceSQL)) {
	            balanceCheckStmt.setString(1, upiId);
	            ResultSet rs = balanceCheckStmt.executeQuery();

	            if (rs.next()) {
	                double currentBalance = rs.getDouble("Balance");
	                if (currentBalance < amount) {
	                    System.out.println("Insufficient balance to pay the bill.");
	                    return;
	                }
	            } else {
	                System.out.println("UPI ID not found.");
	                return;
	            }
	        }

	        // Insert the bill record
	        int rowsInserted = pstatement.executeUpdate();
	        if (rowsInserted > 0) {
	            // Debit the account balance if the bill record was successfully inserted
	            try (PreparedStatement updateBalanceStmt = con.prepareStatement(updateBalanceSQL)) {
	                updateBalanceStmt.setDouble(1, amount);
	                updateBalanceStmt.setString(2, upiId);
	                int rowsUpdated = updateBalanceStmt.executeUpdate();
	                if (rowsUpdated > 0) {
	                    System.out.println("Bill payment successful. Amount debited: " + amount);
	                } else {
	                    System.out.println("Failed to debit the account balance.");
	                }
	            }
	        }

	    } catch (SQLException e) {
	        System.out.println("Error processing bill payment: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
    // Update an existing bill record
    public void updateBill(Connection con, Scanner scanner) {
        System.out.print("Enter Bill ID to update: ");
        String billId = scanner.next();

        System.out.print("Enter new Amount: ");
        double amount = scanner.nextDouble();

        String updateSQL = "UPDATE Bills SET Amount = ? WHERE Bill_id = ?";
        try (PreparedStatement pstatement = con.prepareStatement(updateSQL)) {
            pstatement.setDouble(1, amount);
            pstatement.setString(2, billId);

            int rowsUpdated = pstatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Record updated successfully. Rows affected: " + rowsUpdated);
            } else {
                System.out.println("No record found with Bill ID: " + billId);
            }
        } catch (SQLException e) {
            System.out.println("Error updating bill: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Delete a bill record
    public void deleteBill(Connection con, Scanner scanner) {
        System.out.print("Enter Bill ID to delete: ");
        String billId = scanner.next();

        String deleteSQL = "DELETE FROM Bills WHERE Bill_id = ?";
        try (PreparedStatement pstatement = con.prepareStatement(deleteSQL)) {
            pstatement.setString(1, billId);

            int rowsDeleted = pstatement.executeUpdate();
            System.out.println("Record deleted successfully. Rows affected: " + rowsDeleted);
        } catch (SQLException e) {
            System.out.println("Error deleting bill: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Select and display all bill records
    public void getAllBills(Connection con) {
        String selectSQL = "SELECT * FROM Bills";
        try (PreparedStatement pstatement = con.prepareStatement(selectSQL);
             ResultSet resultSet = pstatement.executeQuery()) {

            System.out.println("--------------------------------------------------------");
            System.out.println("Bill ID | Type                | Amount | Pay From UPI ID | Bill Date");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("Bill_id") + " | " +
                        resultSet.getString("Type") + " | " +
                        resultSet.getDouble("Amount") + " | " +
                        resultSet.getString("Pay_From_Upi_id") + " | " +
                        resultSet.getTimestamp("Bill_Date"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching bills: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
