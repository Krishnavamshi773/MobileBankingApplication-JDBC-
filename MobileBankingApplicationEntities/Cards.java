package MobileBankingApplicationEntities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import MobileBankingApplicationCRUD.CardsCRUD;

public class Cards {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobilebank", "root", "Vamshi123");
CardsCRUD b=new CardsCRUD();
Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select Your Choice:\n1. Add Card\n2. Update Card\n3. Delete  Card\n4. Get AllCards\n5. Exit");
            System.out.print("Enter your Option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> b.insertCard(connection, scanner);
                case 2 -> b.updateCard(connection, scanner);
                case 3 -> b.deleteCard(connection, scanner);
                case 4 -> b.getAllCards(connection);
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
