package util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.ListIterator;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Model.Avis;

public class Crawlers {
	
	
	/**
	 * Crawler for Boamp (GUO's WORKING ON)
	 */
	
	// Scrawler ONLY for https://boamp.fr/avis/liste
	public ArrayList<String> getLinksBOAMP(){
		
		String AuthFileName = this.getClass().getClassLoader().getResource("jssecacerts").getPath();
		System.setProperty("javax.net.ssl.trustStore",AuthFileName);
		
		Map<String,String> params = new HashMap<String, String>();
		
		params.put(Const.CONF1,"0");
		params.put(Const.CONF2,"0");
		
		// Add all the keywords corresponding
//		for(String str : Const.listDescripteur) {
//			params.put(Const.DESCRIPTION, str);
//		}
		params.put("descripteur[]", "mc38");
		params.put("descripteur[]", "mc283");
		
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
			Elements test = doc.getElementsByClass("search-result-caption");
			System.out.println(test.html());
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
	        System.out.println(listLinks.size());
	        return listLinks;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
        
	}
	
	public static void main(String[] args) {
		ArrayList<String> urls = new ArrayList<String>();
		Crawlers crawler = new Crawlers();
//		urls = crawler.getLinksBOAMP();
//		
//		for(String url : urls) {
//			System.out.println(url);
//		}
		//System.out.println(crawler.FranceMarcheCrawler());
		
		try {
			String result = HTTPRequest.sendPost("https://www.marches-publics.gouv.fr/?page=entreprise.EntrepriseAdvancedSearch&searchAnnCons", null);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*****************************************************/
	
	/**
	 * Crawler for Marche-publics(info)(ZHI's WORKING ON)
	 */
	public void MarchepublicsInfoCrawler() {
		
	}
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
	 * Crawler for FranceMarche
	 * @author ZHI
	 * @param
	 * @return list of the avis
	 */
 	public ArrayList<Avis> FranceMarcheCrawler() {
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
						+"%2526b%253D0%2526s%253D%2526sl%253Dxml%2526lang%253Dfr%2526hf%253D15%2526r%253Df%25252FtypeDePrestation%25252F"
						+params.get("typeDePresentation1")
						+"%2526r%253Df%25252FtypeDAnnonce%25252F"
						+params.get("typeDAnnonce2")
						+"%2526r%253Df%25252FsecteurDActivite%25252Fservices%252Bde%252Btechnologies%252Bde%252Bl%252527information%25252C%252Bconseil%25252C%252Bdeveloppement%252Bde%252Blogiciels%25252C%252Binternet%252Bet%252Bappui%2526r%253Df%25252F"
						+"localisation%25252F"
						+params.get("localisation1")
						+"%2526r%253Df%25252FtypeDePrestation%25252F"
						+params.get("typeDePresentation2")
						+"&search=&date=&alerte_Name=";
				//System.out.println(urlFM);
				String result = null;
				try {
					result = HTTPRequest.sendGET(urlFM);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println(result);
				
				//parse the HTML data
				Document doc = Jsoup.parse(result);
				ArrayList<String> listLinks = new ArrayList<String>();
				ArrayList<String> listTitre = new ArrayList<String>();
				ArrayList<String> listDate = new ArrayList<String>();
				
				Elements eles = doc.getElementsByAttributeValueStarting("href", "https://www.francemarches.com/appel-offre/");
				for(Element ele : eles) {
					listLinks.add(ele.attr("href"));
				}				
				
				Elements eles2 = doc.getElementsByClass("dateParution");
				
				for(Element ele : eles2) {
					//System.out.println(ele.text());
					listDate.add(ele.text());
				}
				Elements elesTitre = doc.getElementsByClass("titre");

				for(Element ele : elesTitre) {
					//System.out.println(ele.text());
					listTitre.add(ele.text());
				}
				//using LinkedHashSet to delete the repeated members
				LinkedHashSet<String> setLinks = new LinkedHashSet<String>(listLinks);
				listLinks = new ArrayList<String>(setLinks);
				LinkedHashSet<String> setTitre = new LinkedHashSet<String>(listTitre);
				listTitre = new ArrayList<String>(setTitre);

				
//				System.out.println(listLinks);
//				System.out.println(listTitre);
//				System.out.println(listDate);
				
				//create the list fo the avis
				ArrayList<Avis> avisList = new ArrayList<Avis>();
				for(int i = 0;i<listLinks.size();i++) {
					avisList.add(new Avis(listDate.get(i),listTitre.get(i),listLinks.get(i)));
				}
				
				
				System.out.println(avisList.size());
				
				/**for the others pages**/
				
				//define the number of the pages(including the first page)
				int pageNum = 2;
				//for the others pages
				for(int i=1;i<pageNum;i++) {
					
					//define the url of the other pages
					urlFM = "https://www.francemarches.com/search?c=q%253D%252523all%252BAND%252Bfm_class_date_cloture_dt%25253E%25253D"
							+params.get("date_local")
							+"%252BAND%252Bfm_class_date_parution_dt%25253E%25253D"
							+params.get("date_parution_debut")
							+"%252BAND%252Bfm_class_date_parution_dt%25253C%25253D"
							+params.get("date_parution_fin")
							+"%2526b%253D"
							+15*(i-1)
							+"%2526s%253D%2526sl%253Dxml%2526lang%253Dfr%2526hf%253D15%2526r%253Df%25252FtypeDePrestation%25252F"
							+params.get("typeDePresentation1")
							+"%2526r%253Df%25252FtypeDAnnonce%25252F"
							+params.get("typeDAnnonce2")
							+"%2526r%253Df%25252FsecteurDActivite%25252Fservices%252Bde%252Btechnologies%252Bde%252Bl%252527information%25252C%252Bconseil%25252C%252Bdeveloppement%252Bde%252Blogiciels%25252C%252Binternet%252Bet%252Bappui"
							+"%2526r%253Df%25252Flocalisation%25252F"
							+params.get("localisation1")
							+"%2526r%253Df%25252FtypeDePrestation%25252F"
							+params.get("typeDePresentation2")
							+"&b="
							+15*i
							+"&s=&sa=&search=&date_parution_debut=%3E="
							+params.get("date_parution_debut")
							+"&date_parution_fin=%3C="
							+params.get("date_parution_fin");
					
					//System.out.println(urlFM);
					
					try {
						result = HTTPRequest.sendGET(urlFM);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//parse the HTML data
					Document doc2 = Jsoup.parse(result);
					ArrayList<String> listLinks2 = new ArrayList<String>();
					ArrayList<String> listTitre2 = new ArrayList<String>();
					ArrayList<String> listDate2 = new ArrayList<String>();
					
					Elements eles3 = doc2.getElementsByAttributeValueStarting("href", "https://www.francemarches.com/appel-offre/");
					for(Element ele : eles3) {
						listLinks2.add(ele.attr("href"));
					}
					
					Elements eles4 = doc2.getElementsByClass("dateParution");
					
					for(Element ele : eles4) {
						//System.out.println(ele.text());
						listDate2.add(ele.text());
					}
					
					Elements eles5 = doc2.getElementsByClass("titre");
					for(Element ele : eles5) {
						//System.out.println(ele.text());
						listTitre2.add(ele.text());
					}
					//using LinkedHashSet to delete the repeated members
					 setLinks = new LinkedHashSet<String>(listLinks2);
					listLinks2 = new ArrayList<String>(setLinks);
					 setTitre = new LinkedHashSet<String>(listTitre2);
					listTitre2 = new ArrayList<String>(setTitre);
					
//					System.out.println(listLinks2);
//					System.out.println(listTitre2);
//					System.out.println(listDate2);
					for(int j = 0;j<listLinks2.size();j++) {
						avisList.add(new Avis(listDate2.get(j),listTitre2.get(j),listLinks2.get(j)));
					}
					
				}
				
				System.out.println(avisList.size());
				return avisList;
				
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





