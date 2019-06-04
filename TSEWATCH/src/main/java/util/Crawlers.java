package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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





