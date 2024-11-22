package MobileBankingApplicationCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoansCRUD {
    private AccountsCRUD accountsCRUD;

    // Constructor to initialize AccountsCRUD instance
    public LoansCRUD() {
        accountsCRUD = new AccountsCRUD();
    }

    // Insert a new loan record and credit the amount to the associated account
    public void insertLoan(Connection con, Scanner scanner) {
        String insertSQL = "INSERT INTO Loans (Loan_id, Upi_id, Interest_Rate, Amount, Term) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstatement = con.prepareStatement(insertSQL)) {
            String loanId = generateLoanId();  // Generate 12-digit Loan ID
            pstatement.setString(1, loanId);

            System.out.print("Enter UPI ID (linked to the account): ");
            String upiId = scanner.next();
            pstatement.setString(2, upiId);

            System.out.print("Enter Interest Rate (e.g., 5.75): ");
            double interestRate = scanner.nextDouble();
            pstatement.setDouble(3, interestRate);

            System.out.print("Enter Loan Amount: ");
            double amount = scanner.nextDouble();
            pstatement.setDouble(4, amount);

            System.out.print("Enter Loan Term (in months): ");
            int term = scanner.nextInt();
            pstatement.setInt(5, term);

            int rowsInserted = pstatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Loan record inserted successfully with Loan ID: " + loanId);

                // Credit the loan amount to the UPI ID in the Accounts table
                boolean credited = accountsCRUD.creditBalance(con, upiId, amount);
                if (credited) {
                    System.out.println("Loan amount credited to account with UPI ID: " + upiId);
                } else {
                    System.out.println("Error: UPI ID not found or balance update failed.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error inserting loan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to generate a random 12-digit loan ID
    private String generateLoanId() {
        long randomNumber = (long) (Math.random() * 1_000_000_000_000L);
        return String.format("%012d", randomNumber);
    }

    // Other methods remain unchanged

    // Update an existing loan record
    public void updateLoan(Connection con, Scanner scanner) {
        System.out.print("Enter Loan ID to update: ");
        String loanId = scanner.next();

        System.out.print("Enter new Interest Rate: ");
        double interestRate = scanner.nextDouble();

        System.out.print("Enter new Loan Amount: ");
        double amount = scanner.nextDouble();

        System.out.print("Enter new Loan Term (in months): ");
        int term = scanner.nextInt();

        String updateSQL = "UPDATE Loans SET Interest_Rate = ?, Amount = ?, Term = ? WHERE Loan_id = ?";
        try (PreparedStatement pstatement = con.prepareStatement(updateSQL)) {
            pstatement.setDouble(1, interestRate);
            pstatement.setDouble(2, amount);
            pstatement.setInt(3, term);
            pstatement.setString(4, loanId);

            int rowsUpdated = pstatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Loan record updated successfully. Rows affected: " + rowsUpdated);
            } else {
                System.out.println("No record found with Loan ID: " + loanId);
            }
        } catch (SQLException e) {
            System.out.println("Error updating loan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Delete a loan record
    public void deleteLoan(Connection con, Scanner scanner) {
        System.out.print("Enter Loan ID to delete: ");
        String loanId = scanner.next();

        String deleteSQL = "DELETE FROM Loans WHERE Loan_id = ?";
        try (PreparedStatement pstatement = con.prepareStatement(deleteSQL)) {
            pstatement.setString(1, loanId);

            int rowsDeleted = pstatement.executeUpdate();
            System.out.println("Loan record deleted successfully. Rows affected: " + rowsDeleted);
        } catch (SQLException e) {
            System.out.println("Error deleting loan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Select and display all loan records
    public void getAllLoans(Connection con) {
        String selectSQL = "SELECT * FROM Loans";
        try (PreparedStatement pstatement = con.prepareStatement(selectSQL);
             ResultSet resultSet = pstatement.executeQuery()) {

            System.out.println("--------------------------------------------------------");
            System.out.println("Loan ID | UPI ID | Interest Rate | Amount | Term");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("Loan_id") + " | " +
                        resultSet.getString("Upi_id") + " | " +
                        resultSet.getDouble("Interest_Rate") + " | " +
                        resultSet.getDouble("Amount") + " | " +
                        resultSet.getInt("Term"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching loans: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
