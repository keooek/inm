package inmjava;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;


public class Inm {
	public static void main(String[] args) throws AddressException, MessagingException {
		//Mail mail = new Mail();
		Database.initialize();
		 System.out.println("IDEALISTA-----------------------------------------------------------------");
		 Idealista idea = new Idealista(); 
		 System.out.println("FOTOCASA-----------------------------------------------------------------"); 
		 //Thread fcasa = new Thread(new Fotocasa());
		 //fcasa.start();
		 //Fotocasa fcasa =new Fotocasa(); 
		 System.out.println("VIBBO-----------------------------------------------------------------"); 
		 //Vibbo vibbo = new Vibbo();
		//Database db = new Database("database");
		//Database dbdata = new Database();
		 try {
			Database.shutdown();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 
	}
}
