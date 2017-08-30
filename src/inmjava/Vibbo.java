package inmjava;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Vibbo {
	ArrayList<Inmueble> inmuebleList = new ArrayList<>();

	Vibbo() throws SQLException {
		try {
			Document doc = Jsoup
					.connect(
							"https://www.vibbo.com/venta-de-solo-pisos-bilbao/?ca=48_s&a=19&m=48020&itype=6&fPos=148&fOn=sb_location")
					.proxy("localhost", 8888)
					.userAgent(
							"Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.timeout(180000).ignoreHttpErrors(true).followRedirects(true).get();
			// Elements links = doc.select("a[href]");
			// Elements links = doc.select("a[href^=/inmueble/]");
			Elements links = doc.select("a[href*=a10]");
			// Elements links = doc.select("a[href^=/inmueble/],a[href*=tel]");
			print("\nLinks: (%d)", links.size());
			for (Element link : links) {
				print("%s (%s)", link.attr("abs:href"), trim(link.text(), 150));
				// Extract id from URL
				String[] cortarString = link.attr("abs:href").split("/");
				String id = cortarString[5];
				System.out.println(id);
				// Extract URL cleaned parameters
				cortarString = link.attr("abs:href").split("\\?");
				String url = cortarString[0];
				System.out.println(url);
				// a106343591
				// https://www.vibbo.com/vizcaya/piso-en-zorroza/a106343591/
				// https://www.vibbo.com/vizcaya/piso-en-zorroza/a106343591/?ca=48_s&st=s&c=58
				Inmueble inm = new Inmueble();
				inm.setId(id);

				if ((inmuebleList.contains(inm) == false) && ("0".equals(Database.exists_reg(id)))) {
					inm.setUrl(url);
					inm.setSource("Vibbo");
					System.out.println(inm.getId() + "     " + inm.getUrl());
					Document doc_inm = Jsoup.connect(inm.getUrl()).proxy("localhost", 8888)
							.userAgent(
									"Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
							.timeout(180000).ignoreHttpErrors(true).followRedirects(true).get();
					inm.setZona(doc_inm.getElementsByClass("map-ad-location__name").text());
					inm.setDireccion(doc_inm.getElementsByClass("productTitle").text());
					inm.setPrecio(Integer.valueOf(doc_inm.getElementsByClass("price").text().replace("€", "").replace(".", "")));
					inm.setTelefono(000000);
					inm.setDescripcion(doc_inm.getElementsByClass("descriptionLong").text());
					inm.setVendedor(doc_inm.getElementsByClass("sellerBox__info__name").text());
					Elements links_tel = doc_inm.select("img[src*=numbers]");
					for (Element elem_inm : links_tel) {
						print(" * %s (%s)", elem_inm.tagName(), elem_inm.attr("src"));
						// inm.setTelUrl1(links_tel);
						inm.setTelUrls("https:" + elem_inm.attr("src"));
					}
					// System.out.println(inm.getDescripcion() + "\n" +
					// inm.getPrecio() + "\n" + inm.getZona() + "\n" +
					// inm.getVendedor() + inm.getTelUrls() + "\n\n");

					// System.out.println(inmuebleList.contains(inm));
					inmuebleList.add(inm);
				}

			}
			Database.add_rows(inmuebleList);
			Database.query("select * from Idealista");

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