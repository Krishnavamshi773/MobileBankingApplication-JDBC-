package MobileBankingApplicationEntities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import MobileBankingApplicationCRUD.UsersCRUD;
public class Users {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//Class.forName("com.mysql.jdbc.Driver");
		Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/mobilebank","root","Vamshi123");
		Statement stmt=connection.createStatement();
		UsersCRUD uc=new UsersCRUD();		
		while(true) {
			System.out.println("Select Your Choice.\n 1.Add Users \n2.Update Users \n3."
					+ "Delete User\n 4.Get all Users \n5.exit:\nEnter your Option:");
			Scanner scanner=new Scanner(System.in);
			int choice=scanner.nextInt();
			switch(choice) {
			case 1: uc.insertUsers(connection,scanner);
			        break;
			case 2: uc.updateUsers(connection,scanner);
		       break;
			case 3:uc.deleteUsers(connection,scanner);
		       break;
			case 4:uc.getAllUsers(connection);
		       break;
			case 5:System.out.println("Thank you for using this App:Bye");
				System.exit(0);
		       break;
			default:System.out.println("Enter valid option");
		       break;
			}
		}
	}
}
