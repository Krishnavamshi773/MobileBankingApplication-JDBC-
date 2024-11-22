package MobileBankingApplicationEntities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import MobileBankingApplicationCRUD.AccountsCRUD;

public class Accounts extends AccountsCRUD {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Establish connection to the database
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobilebank", "root", "Vamshi123");
        
        // Create instance of AccountsCRUD to perform operations
        AccountsCRUD ac = new AccountsCRUD();
        
        while (true) {
            System.out.println("Select Your Choice:\n"
                    + "1. Add Account\n"
                    + "2. Update Account\n"
                    + "3. Delete Account\n"
                    + "4. Get All Accounts\n"
                    + "5. Exit\nEnter your Option:");
            
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    ac.insertAccount(connection, scanner);
                    break;
                case 2:
                    ac.updateAccount(connection, scanner);
                    break;
                case 3:
                    ac.deleteAccount(connection, scanner);
                    break;
                case 4:
                    ac.getAllAccounts(connection);
                    break;
                case 5:
                    System.out.println("Thank you for using this App. Goodbye!");
                    connection.close();
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please enter a valid option.");
                    break;
            }
        }
    }
}
