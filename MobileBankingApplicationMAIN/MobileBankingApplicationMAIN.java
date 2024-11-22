package MobileBankingApplicationMAIN;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import MobileBankingApplicationCRUD.*;
public class MobileBankingApplicationMAIN {
   public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobilebank", "root", "Vamshi123");
             Scanner scanner = new Scanner(System.in)) {
            while (true) {
                // Display main menu
                System.out.println("Select Your Choice:");
                System.out.println("1. Manage Users");
                System.out.println("2. Manage Accounts");
                System.out.println("3. Money Transfer");
                System.out.println("4. Bills Management");
                System.out.println("5. Manage Cards");
                System.out.println("6. Loans Management");
                System.out.println("7. Bookings Management");
                System.out.println("8. Exit");   
                System.out.print("Enter your option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> manageUsers(connection, scanner);
                    case 2 -> manageAccounts(connection, scanner);
                    case 3 -> manageMoneyTransfers(connection, scanner);
                    case 4 -> manageBills(connection, scanner);
                    case 5 -> manageCards(connection, scanner);
                    case 6 -> manageLoans(connection, scanner);
                    case 7 -> manageBookings(connection, scanner);
                    case 8 -> {
                        System.out.println("Thank you for using the Mobile Banking Application");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database connection failed.");
        }
    }
    private static void manageUsers(Connection connection, Scanner scanner) {
        UsersCRUD uc = new UsersCRUD();
        while (true) {
            System.out.println("User Management:");
            System.out.println("1. Add User\n2. Update User\n3. Delete User\n4. Get all Users\n5. Return to Main Menu");
            System.out.print("Enter your option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> uc.insertUsers(connection, scanner);
                case 2 -> uc.updateUsers(connection, scanner);
                case 3 -> uc.deleteUsers(connection, scanner);
                case 4 -> uc.getAllUsers(connection);
                case 5 -> { return; } // Exit to main menu
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void manageAccounts(Connection connection, Scanner scanner) {
        AccountsCRUD ac = new AccountsCRUD();
        while (true) {
            System.out.println("Accounts Management:");
            System.out.println("1. Create Account\n2. Update Account\n3. Delete Account\n4. Get All Accounts\n5. Return to Main Menu");
            System.out.print("Enter your option: ");
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1 -> ac.insertAccount(connection, scanner);
                case 2 -> ac.updateAccount(connection, scanner);
                case 3 -> ac.deleteAccount(connection, scanner);
                case 4 -> ac.getAllAccounts(connection);
                case 5 -> { return; } // Exit to main menu
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void manageMoneyTransfers(Connection connection, Scanner scanner) {
        MoneyTransferCRUD m = new MoneyTransferCRUD();
        while (true) {
            System.out.println("Money Transfer Management:");
            System.out.println("1. Make Money Transfer\n2. Get All Transfers\n3. Return to Main Menu");
            System.out.print("Enter your option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> m.insertTransfer(connection, scanner);
               // case 2 -> m.updateTransfer(connection, scanner);
               // case 3 -> m.deleteTransfer(connection, scanner);
                case 2 -> m.getAllTransfers(connection);
                case 3 -> { return; } // Exit to main menu
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void manageBills(Connection connection, Scanner scanner) {
        BillsCRUD b = new BillsCRUD();
        while (true) {
            System.out.println("Bills Management:");
            System.out.println("1. Make Bill Payment\n2. Update Bill\n3. Delete Bill\n4. Get All Bills\n5. Return to Main Menu");
            System.out.print("Enter your option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> b.insertBill(connection, scanner);
                case 2 -> b.updateBill(connection, scanner);
                case 3 -> b.deleteBill(connection, scanner);
                case 4 -> b.getAllBills(connection);
                case 5 -> { return; } // Exit to main menu
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void manageCards(Connection connection, Scanner scanner) {
        CardsCRUD c = new CardsCRUD();
        while (true) {
            System.out.println("Cards Management:");
            System.out.println("1. Add Card\n2. Update Card\n3. Delete Card\n4. Get All Cards\n5. Return to Main Menu");
            System.out.print("Enter your option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> c.insertCard(connection, scanner);
                case 2 -> c.updateCard(connection, scanner);
                case 3 -> c.deleteCard(connection, scanner);
                case 4 -> c.getAllCards(connection);
                case 5 -> { return; } // Exit to main menu
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void manageLoans(Connection connection, Scanner scanner) {
        LoansCRUD l = new LoansCRUD();
        while (true) {
            System.out.println("Loans Management:");
            System.out.println("1. Take Loan\n2. Update Loan\n3. Delete Loan\n4. Get All Loans\n5. Return to Main Menu");
            System.out.print("Enter your option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> l.insertLoan(connection, scanner);
                case 2 -> l.updateLoan(connection, scanner);
                case 3 -> l.deleteLoan(connection, scanner);
                case 4 -> l.getAllLoans(connection);
                case 5 -> { return; } // Exit to main menu
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void manageBookings(Connection connection, Scanner scanner) {
        BookingsCRUD bk = new BookingsCRUD();
        while (true) {
            System.out.println("Bookings Management:");
            System.out.println("1. Make Booking\n2. Update Booking\n3. Cancel Booking\n4. Get All Bookings\n5. Return to Main Menu");
            System.out.print("Enter your option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> bk.insertBooking(connection, scanner);
                case 2 -> bk.updateBooking(connection, scanner);
                case 3 -> bk.deleteBooking(connection, scanner);
                case 4 -> bk.getAllBookings(connection);
                case 5 -> { return; } // Exit to main menu
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
