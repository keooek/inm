package inmjava;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;


public class Inm {
	public static void main(String[] args) throws AddressException, MessagingException, SQLException, InterruptedException {
		//Mail mail = new Mail();
		 Database.initialize();
		 
		 System.out.println("IDEALISTA-----------------------------------------------------------------");
		 Idealista idea = new Idealista(); 
		 //Thread idea = new Thread(new Idealista());
		 //idea.start();
		 System.out.println("FOTOCASA-----------------------------------------------------------------"); 
		 //Fotocasa fcasa =new Fotocasa(); 
		 //Thread fcasa = new Thread(new Fotocasa());
		 //fcasa.start();
		 System.out.println("VIBBO-----------------------------------------------------------------"); 
		 Vibbo vibbo = new Vibbo();
		 try {
			Database.shutdown();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 
	}
}
