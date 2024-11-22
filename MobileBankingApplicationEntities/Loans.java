package MobileBankingApplicationEntities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import MobileBankingApplicationCRUD.LoansCRUD;


public class Loans {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobilebank", "root", "Vamshi123");
        LoansCRUD loansCRUD = new LoansCRUD();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select Your Choice:\n1. Take Loan\n2. Update Loan\n3. Delete Loan\n4. Get All Loans\n5. Exit");
            System.out.print("Enter your Option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> loansCRUD.insertLoan(connection, scanner);
                case 2 -> loansCRUD.updateLoan(connection, scanner);
                case 3 -> loansCRUD.deleteLoan(connection, scanner);
                case 4 -> loansCRUD.getAllLoans(connection);
                case 5 -> {
                    System.out.println("Thank you for using the Loan management app. Goodbye!");
                    connection.close();
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
