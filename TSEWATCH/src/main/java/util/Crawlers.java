package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Model.AvisST;

public class Crawlers {
	
	
	/**
	 * Crawler for Boamp (GUO's WORKING ON)
	 */
	
	// Scrawler ONLY for https://boamp.fr/avis/liste
	public ArrayList<String> getLinksBOAMP(){
		
		String AuthFileName = this.getClass().getClassLoader().getResource("jssecacerts").getPath();
		System.setProperty("javax.net.ssl.trustStore",AuthFileName);
		
		Map<String,String> params = new HashMap<String, String>();
		
		//params.put(Const.CONF1,"0");
		//params.put(Const.CONF2,"0");
		
		// Add all the keywords corresponding
		for(String str : Const.listDescripteur) {
			params.put(Const.DESCRIPTION, str);
		}
		
		// 5 -> Cover all 1-4 options
		params.put(Const.AVIS, "5");
		
		
		// Get result
		String result;
		try {
			result = HTTPRequest.sendPost(Const.BOAMP, params);
			Document doc = Jsoup.parse(result);
			/**
			 *  To verify if the results exist in more than one page
			 */
			ArrayList<String> linksOfPages = new ArrayList<String>();
			Elements elesPageIndex = doc.getElementsByAttributeValueStarting("href", "/avis/page?page=");
			for(Element ele : elesPageIndex)
			{
				if(!linksOfPages.contains("https://www.boamp.fr" + ele.attr("href")))
					linksOfPages.add("https://www.boamp.fr" + ele.attr("href"));
			}
			
			
			ArrayList<String> listLinks = new ArrayList<String>();
			Elements hrefs = doc.getElementsByAttributeValueContaining("href","/avis/detail/");
			for(Element href : hrefs) {
				if(!listLinks.contains("https://www.boamp.fr" + href.attr("href")))
					listLinks.add("https://www.boamp.fr" + href.attr("href"));
			}
			
			ListIterator li = listLinks.listIterator();
	        while(li.hasNext()){
	            Object obj = li.next();
	            int length = obj.toString().split("/").length;
	            li.set("https://www.boamp.fr/avis/pdf/" + obj.toString().split("/")[length-2]);
	        }
	        return listLinks;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
        
	}
	
	public static void main(String[] args) {
		Date date=new Date();
		System.out.println(date); 
		SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
		System.out.println(dateFormat.format(date).toString()); 
		
	}
	
	
	/*****************************************************/
	
	/**
	 * Crawler for Marche-publics(info)
	 */
	
	/*****************************************************/
	
	/**
	 * Crawler for Marche-publics(gouv)
	 */
	
	/*****************************************************/
	
	/**
	 * Crawler for Auvergnerhonealpes
	 */
	
	/*****************************************************/
	
	/**
	 * Crawler for Ted.europa
	 */
	
	/*****************************************************/
	
	/**
	 * Crawler for FranceMarche (ZHI's WORKING ON)
	 */
	public void FranceMarcheCrawler() {
		//define the link's url to the website
				String urlFM = "https://www.francemarches.com/search?search=";
				
				//define the map of the parametres(settings)
				Map<String,String> params = new HashMap<String, String>();
				
				//define the type de presentation(domain d'activité): travaux , services,fournitures and autre
				String typeDePresentation1 = "travaux";
				String typeDePresentation2 = "services";
				String typeDePresentation3 = "fournitures";
				String typeDePresentation4 = "autre";
				
				params.put("typeDePresentation1", typeDePresentation1);
				params.put("typeDePresentation2", typeDePresentation2);
				params.put("typeDePresentation3", typeDePresentation3);
				params.put("typeDePresentation4", typeDePresentation4);
				
				
				//define the type de annonce(type d'avis) :AAPC, Avis d'attribution, Avis rectificatif, Autre
				String typeDAnnonce1 = "avis+d%27attribution";
				String typeDAnnonce2 = "aapc";
				String typeDAnnonce3 = "avis%252Brectificatif";
				String typeDAnnonce4 = "autre";
				
				params.put("typeDAnnonce1", typeDAnnonce1);
				params.put("typeDAnnonce2", typeDAnnonce2);
				params.put("typeDAnnonce3", typeDAnnonce3);
				params.put("typeDAnnonce4", typeDAnnonce4);
				
				//define the localisation(Lieu d'exéution) 'number max' = 8 :
				//auvergne-rhone-alpes , bourgogne-franche-comte , bretagne , centre+-+val+de+loire , corse , .....
				String localisation1 = "auvergne-rhone-alpes";
				String localisation2 = "bourgogne-franche-comte";
				String localisation3 = "bretagne";
				String localisation4 = "centre+-+val+de+loire";
				
				
				params.put("localisation1", localisation1);
				params.put("localisation2", localisation2);
				params.put("localisation3", localisation3);
				params.put("localisation4", localisation4);
				
				//define "Date de publiccation"
				String date_parution_debut = "2019-05-01";
				String date_parution_fin = "2019-06-01";
				
				params.put("date_parution_debut", date_parution_debut);
				params.put("date_parution_fin", date_parution_fin);
				
				
				//define local date
				Date date=new Date();
				SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
				String date_local = dateFormat.format(date).toString();
				
				params.put("date_local", date_local);
				
				//create the url by all the settings for GET request
				urlFM = "https://www.francemarches.com/search?date_parution_debut=%3E="
						+params.get("date_parution_debut")
						+"&date_parution_fin=%3C="
						+params.get("date_parution_fin")
						+"&c=q%253D%252523all%252BAND%252Bfm_class_date_cloture_dt%25253E%25253D"
						+params.get("date_local")
						+"%2526b%253D0%2526s%253D%2526sl%253Dxml%2526lang%253Dfr%2526hf%253D15%2526r%253Df%25252Flocalisation%25252F"
						+params.get("localisation1")
						+"%2526r%253Df%25252Flocalisation%25252F"
						+params.get("localisation2")
						+"%2526r%253Df%25252FtypeDAnnonce%25252F"
						+params.get("typeDAnnonce1")
						+"%2526r%253Df%25252FtypeDAnnonce%25252F"
						+params.get("typeDAnnonce2")
						+"%2526r%253Df%25252FtypeDePrestation%25252F"
						+params.get(typeDePresentation1)
						+"&search=&date=&alerte_Name=";
				
				
				
						
				
				
	}
	/*****************************************************/
	
	/**
	 * Crawler for E-marchespublics
	 */
	
	/*****************************************************/
	
	/**
	 * Crawler for Centraledesmarches
	 */
	
	/*****************************************************/
	
	/**
	 * Crawler for Marchesonline
	 */

}





