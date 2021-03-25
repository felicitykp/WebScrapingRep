import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Weather {
	
	
	
	//CONSTRUCTOR
	public Weather() {
		
		System.out.println("Which City's Weather would you like to Search?  ");
		
		Scanner s = new Scanner(System.in);
		String city = s.nextLine();
		
		getInfo(city);
		
	}
	
	public void getInfo(String city) {
		
		try {
			
			Document doc = Jsoup.connect("https://www.google.com/search?q=" + city +"+weather").get();
			
			Element loc = doc.select("#wob_loc").first();
			System.out.println("\n" + loc.text() + " Weather:\n");
			
			Element temp = doc.select("#wob_tm").first();
			System.out.println("The Temperature is: " + temp.text() + "F");
			
			Element type = doc.select("#wob_dc").first();
			System.out.println("The Weather Type is: " + type.text());
			
			Element rain = doc.select("#wob_pp").first();
			System.out.println("There is a " + rain.text() + " chance of Precipitation");
			
			Element hum = doc.select("#wob_hm").first();
			System.out.println("There is " + hum.text() + " Humitidy today");
			
			
		} catch (IOException e){
			System.out.println("Couldn't connect");
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		new Weather();
	

	}
}
