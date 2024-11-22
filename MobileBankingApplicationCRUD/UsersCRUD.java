package MobileBankingApplicationCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import MobileBankingApplicationException.InvalidEmailException;
import MobileBankingApplicationException.InvalidUpiIdException;

public class UsersCRUD {

	PreparedStatement pstatement;
	Scanner sc;
	public void insertUsers(Connection con, Scanner scanner) {
        String insertSQL = "INSERT INTO Users (upi_id, Username, Password, Email, Phone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstatement = con.prepareStatement(insertSQL)) {
            System.out.println("Enter values for ADD the User  \n Enter UPI ID of the User \n UPI ID must end with '@paytm', '@ybl', '@apl', or '@oksbi:");
            sc = scanner;
            String upiId = sc.next();

            // Validate the UPI ID
            if (!upiId.endsWith("@paytm") && !upiId.endsWith("@ybl") && !upiId.endsWith("@apl") && !upiId.endsWith("@oksbi")) {
                throw new InvalidUpiIdException("UPI ID must end with '@paytm', '@ybl', '@apl', or '@oksbi'");
            }
            pstatement.setString(1, upiId);

            System.out.println("Enter name of the User:");
            pstatement.setString(2, sc.next());
            System.out.println("Enter password for User:");
            pstatement.setString(3, sc.next());

            // Validate the email
            System.out.println("Enter Email for User: \nEmail must end with '@gmail.com ");
            String email = sc.next();
            if (!email.endsWith("@gmail.com")) {
                throw new InvalidEmailException("Email must end with '@gmail.com'");
            }
            pstatement.setString(4, email);

            System.out.println("Enter Phone no for User:");
            pstatement.setString(5, sc.next());

            // Execute the query
            int rowsInserted = pstatement.executeUpdate();
            System.out.println("No. of records inserted: " + rowsInserted);

        } catch (SQLException e) {
            System.out.println("Error inserting user: " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidEmailException e) {
            System.out.println("Invalid email: " + e.getMessage());
        } catch (InvalidUpiIdException e) {
            System.out.println("Invalid UPI ID: " + e.getMessage());
        }
    }		//update
   public void updateUsers(Connection con,Scanner scanner) {
    	try {
    		System.out.println("Enter your choice\n1.update Username\n2.update Password\n3.update Email\n4.update Phone");
    		sc=scanner;
    		int option=sc.nextInt();
    		switch(option) {
    		case 1:pstatement=con.prepareStatement("update Users set Username=?  where Upi_id=?");
    		      System.out.println("enter Username name for Upi_id");       
    		      pstatement.setString(1, sc.next());
    		      pstatement.setString(2, sc.next());
			      System.out.println("Update records: "+pstatement.executeUpdate());
			      break;
			case 2:pstatement=con.prepareStatement("update Users set Password=?  where Upi_id=?");
		      System.out.println("enter Password for Upi_id");       
		      pstatement.setString(1, sc.next());
		      pstatement.setString(2, sc.next());
		      System.out.println("Update records: "+pstatement.executeUpdate());
		          break; 
    		case 3:pstatement=con.prepareStatement("update Users set Email=?  where Upi_id=?");
		      System.out.println("enter Email for Upi_id");       
		      pstatement.setString(1, sc.next());
		      pstatement.setString(2, sc.next());
		      System.out.println("Update records: "+pstatement.executeUpdate());
		      break;
    		case 4:pstatement=con.prepareStatement("update Users set Phone=?  where Upi_id=?");
		      System.out.println("enter Phone no for Upi_id");       
		      pstatement.setString(1, sc.next());
		      pstatement.setString(2, sc.next());
		      System.out.println("Update records: "+pstatement.executeUpdate());
		      break;

		      default:System.out.println("u have entered wrong option");
			      break;
			
    		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    //delete
   public void deleteUsers(Connection con,Scanner scanner) {
    	try {
			pstatement=con.prepareStatement("delete from Users where Upi_id=?");
			System.out.println("Enter Upi_id for deleting the record:");
			sc=scanner;
			String Upi_id=sc.next();
			pstatement.setString(1, Upi_id);
			System.out.println("No.of Records deleted:"+pstatement.executeUpdate());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
   //select all 
   public void getAllUsers(Connection con) {
    	try {
			pstatement=con.prepareStatement("select * from Users");
			
			ResultSet rSet = pstatement.executeQuery();
			System.out.println("--------------------------------------------------------------------");
			while(rSet.next())
				System.out.println(rSet.getString(1)+" "+rSet.getString(2)+" "+rSet.getString(3)+" "+
					     rSet.getString(4)+" "+rSet.getString(5));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}