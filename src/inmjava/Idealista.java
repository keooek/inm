package inmjava;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//public class Idealista implements Runnable{   //forma recomendada
//public class Idealista extends Thread{

public class Idealista{

	ArrayList<Inmueble> inmuebleList = new ArrayList<>();

	//public void run() {
	Idealista() throws SQLException {
		try {
			Document doc = Jsoup
					.connect(
							"https://www.idealista.com/venta-viviendas/bilbao/indautxu/zona-indautxu/?ordenado-por=fecha-publicacion-desc")
					.proxy("localhost", 8888)
					.userAgent(
							"Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.timeout(180000).ignoreHttpErrors(true).followRedirects(true).get();
			Elements links = doc.select("a[href^=/inmueble/]");

			for (Element link : links) {
				// Extract id from URL
				String[] cortarString = link.attr("abs:href").split("/");
				String id = cortarString[4];
				System.out.println("\nId: " + id);

				String url = link.attr("abs:href");
				System.out.println("URL extraido doc global:  " + url);

				Inmueble inm = new Inmueble();
				inm.setId(id);
				//Thread.sleep(2000);
				
				if ((inmuebleList.contains(inm) == false) && ("0".equals(Database.exists_reg(id)))) {
					inm.setUrl(url);
					inm.setSource("Idealista");
					Document doc_inm = Jsoup.connect(inm.getUrl()).proxy("localhost", 8888)
							.userAgent(
									"Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
							.timeout(180000).ignoreHttpErrors(true).followRedirects(true).get();
					//System.out.println(doc_inm.getElementsByClass(".feedback.warning.icon-feedbk-alert"));
					//Lo sentimos, el inmueble 37581459 ya no está publicado en idealista :-|
					//El anunciante lo dio de baja el jueves 24 de agosto de 2017
					inm.setTelefono(
							Integer.valueOf(doc_inm.getElementsByClass("_browserPhone").text().replace(" ", "")));
					System.out.println(inm.getTelefono());
					inm.setPrecio(Integer.valueOf(doc_inm.getElementsByClass("price").first().text().replace("€", "")
							.replace(".", "").replace(" eur", "")));
					System.out.println(inm.getPrecio());
					inm.setDescripcion(doc_inm.getElementsByClass("adCommentsLanguage expandable").text());
					System.out.println(inm.getDescripcion());
					inm.setDireccion(doc_inm.title());
					System.out.println(inm.getDireccion());
					inm.setVendedor(doc_inm.getElementsByClass("advertiser-data txt-soft").text());
					System.out.println(inm.getVendedor());
					inmuebleList.add(inm);
				}

			}
			Database.add_rows(inmuebleList);
			Database.query("select * from Idealista");
			
			
			//Database.add_rows();
		} catch (IOException | NumberFormatException | SQLException e) {
			System.out.println(e);
		//} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}
}
