package MobileBankingApplicationEntities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import MobileBankingApplicationCRUD.MoneyTransferCRUD;

public class MoneyTransfer {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobilebank", "root", "Vamshi123");
        MoneyTransferCRUD transferCRUD = new MoneyTransferCRUD();

        while (true) {
            System.out.println("Select Your Choice:\n1. Make Money Transfer\n2. Get All Transfers\n3. Exit");
            System.out.print("Enter your Option: ");
            Scanner scanner = new Scanner(System.in);

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> transferCRUD.insertTransfer(connection, scanner);
              //  case 2 -> transferCRUD.updateTransfer(connection, scanner);
               // case 3 -> transferCRUD.deleteTransfer(connection, scanner);
                case 2 -> transferCRUD.getAllTransfers(connection);
                case 3 -> {
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
