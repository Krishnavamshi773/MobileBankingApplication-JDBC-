package MobileBankingApplicationEntities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import MobileBankingApplicationCRUD.BillsCRUD;

public class Bills {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobilebank", "root", "Vamshi123");
BillsCRUD b=new BillsCRUD();
Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Select Your Choice:\n1. Make Bill Payment\n2. Update Bill\n3. Delete  Bill\n4. Get AllBills\n5. Exit");
            System.out.print("Enter your Option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> b.insertBill(connection, scanner);
                case 2 -> b.updateBill(connection, scanner);
                case 3 -> b.deleteBill(connection, scanner);
                case 4 -> b.getAllBills(connection);
                case 5 -> {
                    System.out.println("Thank you for using this app. Goodbye!");
                    connection.close();
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
