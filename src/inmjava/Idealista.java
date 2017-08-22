package inmjava;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Idealista {

	ArrayList<Inmueble> inmuebleList = new ArrayList<>();

	Idealista() {
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

				if (inmuebleList.contains(inm) == false) {
					inm.setUrl(url);
					Document doc_inm = Jsoup.connect(inm.getUrl()).proxy("localhost", 8888)
							.userAgent(
									"Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
							.timeout(180000).ignoreHttpErrors(true).followRedirects(true).get();
					inm.setTelefono(
							Integer.valueOf(doc_inm.getElementsByClass("_browserPhone").text().replace(" ", "")));
					System.out.println(inm.getTelefono());
					inm.setPrecio(Integer.valueOf(doc_inm.getElementsByClass("price").first().text().replace("�", "")
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
		} catch (IOException e) {
			System.out.println(e);
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