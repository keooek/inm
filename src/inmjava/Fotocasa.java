package inmjava;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Fotocasa implements Runnable{

	//Fotocasa() {
	public void run(){	
		try {
			Document doc = Jsoup
					.connect(
							"https://www.fotocasa.es/es/comprar/casas/bilbao/todas-las-zonas/l?latitude=43.2584&longitude=-2.9226&combinedLocationIds=724,18,48,420,791,48020,0,0,0")
					.proxy("localhost", 8888)
					.userAgent(
							"Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.timeout(180000).ignoreHttpErrors(true).followRedirects(true).get();
			// Elements links = doc.select("a[href]");
			// Elements links = doc.select("a[href^=/inmueble/]");
			// Elements links = doc.select("a[href*=tel]");
			Elements links = doc.select("a[href^=/vivienda/bilbao/],a[href*=tel]");
			print("\nLinks: (%d)", links.size());
			for (Element link : links) {
				print("%s  (%s)", link.attr("abs:href"), trim(link.text(), 100));
			}
			// String title = doc.title();
			// System.out.println(title);
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