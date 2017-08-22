package inmjava;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
/*
//Import Apache POI, xlsx
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
//Import parser URLs
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
*/

public class Inm {
	public static void main(String[] args) throws AddressException, MessagingException {
		//Mail mail = new Mail();
		
		 System.out.println("IDEALISTA-----------------------------------------------------------------");
		 //Idealista idea = new Idealista(); 
		 System.out.println("FOTOCASA-----------------------------------------------------------------"); 
		 Thread fcasa = new Thread(new Fotocasa());
		 fcasa.start();
		 //Fotocasa fcasa =new Fotocasa(); 
		 System.out.println("VIBBO-----------------------------------------------------------------"); 
		 Vibbo vibbo =new Vibbo();
		 
	}
}
