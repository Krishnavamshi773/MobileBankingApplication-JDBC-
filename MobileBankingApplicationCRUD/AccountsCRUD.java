package MobileBankingApplicationCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import MobileBankingApplicationException.InvalidAccountNumberException;
public class AccountsCRUD {
    PreparedStatement pstatement;
    Scanner sc;
    // Insert account
    public void insertAccount(Connection con, Scanner scanner) {
        String insertSQL = "INSERT INTO Accounts (Account_no, Upi_id, Bank_name, Ac_Holder_Name, Account_type, Balance) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstatement = con.prepareStatement(insertSQL)) {
            System.out.println("Enter details for the new account:");
            sc = scanner;
            
            System.out.print("Enter Account Number (must be 11 digits): ");
            String accountNo = sc.next();

            // Validate account number length
            if (accountNo.length() != 11) {
                throw new InvalidAccountNumberException("Account number must be exactly 11 digits.");
            }
            pstatement.setString(1, accountNo);

            System.out.print("Enter UPI ID (linked to a user): ");
            pstatement.setString(2, sc.next());

            System.out.print("Enter Bank Name: ");
            pstatement.setString(3, sc.next());

            System.out.print("Enter Account Holder Name: ");
            pstatement.setString(4, sc.next());

            System.out.print("Enter Account Type (Savings, Current, Loan): ");
            pstatement.setString(5, sc.next());

            System.out.print("Enter Initial Balance: ");
            pstatement.setDouble(6, sc.nextDouble());

            int rowsInserted = pstatement.executeUpdate();
            System.out.println("No. of records inserted: " + rowsInserted);

        } catch (SQLException e) {
            System.out.println("Error inserting account: " + e.getMessage());
        } catch (InvalidAccountNumberException e) {
            System.out.println("Invalid Account Number: " + e.getMessage());
        }
    }

    // Method to credit an account balance
    public boolean creditBalance(Connection con, String upiId, double amount) throws SQLException {
        return updateBalance(con, upiId, amount);
    }

    // Method to debit an account balance
    public boolean debitBalance(Connection con, String upiId, double amount) throws SQLException {
        return updateBalance(con, upiId, -amount);
    }

    // Update balance method used by credit and debit methods
    private boolean updateBalance(Connection con, String upiId, double amount) throws SQLException {
        String updateBalanceSQL = "UPDATE Accounts SET Balance = Balance + ? WHERE Upi_id = ?";
        try (PreparedStatement pstatement = con.prepareStatement(updateBalanceSQL)) {
            pstatement.setDouble(1, amount);
            pstatement.setString(2, upiId);
            return pstatement.executeUpdate() > 0;
        }
    }
    
    // Update account details
    public void updateAccount(Connection con, Scanner scanner) {
        try {
            System.out.println("Choose a field to update:\n1. Bank Name\n2. Account Holder Name\n3. Account Type\n4. Balance");
            sc = scanner;
            int option = sc.nextInt();
            String updateSQL = null;

            switch (option) {
                case 1:
                    updateSQL = "UPDATE Accounts SET Bank_name = ? WHERE Account_no = ?";
                    System.out.print("Enter new Bank Name: ");
                    break;
                case 2:
                    updateSQL = "UPDATE Accounts SET Ac_Holder_Name = ? WHERE Account_no = ?";
                    System.out.print("Enter new Account Holder Name: ");
                    break;
                case 3:
                    updateSQL = "UPDATE Accounts SET Account_type = ? WHERE Account_no = ?";
                    System.out.print("Enter new Account Type (Savings, Current, Loan): ");
                    break;
                case 4:
                    updateSQL = "UPDATE Accounts SET Balance = ? WHERE Account_no = ?";
                    System.out.print("Enter new Balance: ");
                    break;
                default:
                    System.out.println("Invalid option selected.");
                    return;
            }

            pstatement = con.prepareStatement(updateSQL);
            pstatement.setString(1, sc.next());
            System.out.print("Enter Account Number to update: ");
            pstatement.setString(2, sc.next());

            System.out.println("No. of records updated: " + pstatement.executeUpdate());

        } catch (SQLException e) {
            System.out.println("Error updating account: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Delete account
    public void deleteAccount(Connection con, Scanner scanner) {
        try {
            pstatement = con.prepareStatement("DELETE FROM Accounts WHERE Account_no = ?");
            System.out.print("Enter Account Number to delete: ");
            sc = scanner;
            String accountNo = sc.next();

            pstatement.setString(1, accountNo);
            System.out.println("No. of records deleted: " + pstatement.executeUpdate());

        } catch (SQLException e) {
            System.out.println("Error deleting account: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Retrieve all accounts
    public void getAllAccounts(Connection con) {
        try {
            pstatement = con.prepareStatement("SELECT * FROM Accounts");
            ResultSet rSet = pstatement.executeQuery();
            System.out.println("---------------------------------------------------------");
            while (rSet.next()) {
                System.out.println(
                    "Account No: " + rSet.getString("Account_no") +
                    ", UPI ID: " + rSet.getString("Upi_id") +
                    ", Bank Name: " + rSet.getString("Bank_name") +
                    ", Account Holder Name: " + rSet.getString("Ac_Holder_Name") +
                    ", Account Type: " + rSet.getString("Account_type") +
                    ", Balance: " + rSet.getDouble("Balance")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving accounts: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
