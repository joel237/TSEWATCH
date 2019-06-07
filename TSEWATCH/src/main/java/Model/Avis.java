package Model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.HTTPRequest;

public class Avis {
	String date;
	String titre;
	String link;
	String location = null; //not every site has the information of the location
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Avis(String date,String titre,String link){
		this.date = date;
		this.titre = titre;
		this.link = link;
	}
	public Avis(String date,String titre,String link,String location){
		this.date = date;
		this.titre = titre;
		this.link = link;
		this.location = location;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	
	//for the HTML
	/**
	 * 
	 * @param keyWord
	 * @return true : It has the keyword
	 * 		false: it hasn't it
	 */
	public boolean checkKeyWordHTML(String keyWord) {
		
		//send GET request to the link to get the HTML data
		String result = null;
		try {
			result = HTTPRequest.sendGET(this.link);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String text = null;
		
		// parse the HTML data to get the text data
		Document doc = Jsoup.parse(result);
		Elements eles = doc.getAllElements();
		for (Element ele : eles) {
			text += ele.text();
		}

		return text.contains(keyWord);

	}
	/**
	 * 
	 * @param keyWord (!!not finished!!)
	 * @return
	 * @throws IOException 
	 */
//	public boolean checkKeyWordPDF(String keyWord) throws IOException {
//		URL site = null;
//		try {
//			site = new URL(this.link);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		InputStream in = site.openStream();
//		byte[] buffer = new byte[1024];
//		in.read(buffer);
//		System.out.println(buffer.toString());
//		return false;
//		
//	}
	
	public void print() {
		System.out.println(this.date);
		System.out.println("titre: "+ titre);
		System.out.println("link: "+link);
		System.out.println("location: " +location);
		System.out.println("");
	}
	

	
}
