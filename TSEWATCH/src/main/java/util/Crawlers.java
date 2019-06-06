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
	public ArrayList<String> getLinksBOAMP() {

		String AuthFileName = this.getClass().getClassLoader().getResource("jssecacerts").getPath();
		System.setProperty("javax.net.ssl.trustStore", AuthFileName);

		Map<String, String> params = new HashMap<String, String>();

		params.put(Const.CONF1, "0");
		params.put(Const.CONF2, "0");

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
			 * To verify if the results exist in more than one page
			 */
			Elements test = doc.getElementsByClass("search-result-caption");
			System.out.println(test.html());
			ArrayList<String> linksOfPages = new ArrayList<String>();
			Elements elesPageIndex = doc.getElementsByAttributeValueStarting("href", "/avis/page?page=");
			for (Element ele : elesPageIndex) {
				if (!linksOfPages.contains("https://www.boamp.fr" + ele.attr("href")))
					linksOfPages.add("https://www.boamp.fr" + ele.attr("href"));
			}

			ArrayList<String> listLinks = new ArrayList<String>();
			Elements hrefs = doc.getElementsByAttributeValueContaining("href", "/avis/detail/");
			for (Element href : hrefs) {
				if (!listLinks.contains("https://www.boamp.fr" + href.attr("href")))
					listLinks.add("https://www.boamp.fr" + href.attr("href"));
			}

			ListIterator li = listLinks.listIterator();
			while (li.hasNext()) {
				Object obj = li.next();
				int length = obj.toString().split("/").length;
				li.set("https://www.boamp.fr/avis/pdf/" + obj.toString().split("/")[length - 2]);
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
		// System.out.println(crawler.FranceMarcheCrawler());

//		ArrayList<Avis> avisList = crawler.FranceMarcheCrawler("auvergne-rhone-alpes","2019-05-01","2019-06-01",2);

		ArrayList<Avis> avisList = crawler.proxiLegalesCrawler("orange", 2);

//		ArrayList<Avis> avisList = crawler.marchepublicsInfoCrawler("1,3,7,15,26,38,42,43,63,69,73,74","= 0");
		
		
		
		for(Avis avis:avisList) {
			avis.print();
		}
	}

	/*****************************************************/
	/**
	 * Crawler for ProxiLegales(not finished)
	 * 
	 * @author ZHI
	 * @param the key words and the number of the pages(including the first page) we
	 *            set 10 avis on every page( you can change it in "se_iMaxElementPerPage" )
	 * @return the list of the avis
	 * 
	 */
	public ArrayList<Avis> proxiLegalesCrawler(String filtre, int pageNum) {
		// define the url of the site
		String urlProxi = "https://www.proxilegales.fr/publisher_portail/public/annonce/afficherAnnonces.jsp;jsessionid=1A20E1BB4A2D9511152F7E21406CFB34";
		// "https://www.proxilegales.fr/publisher_portail/public/annonce/afficherAnnonces.jsp;jsessionid=52E4BDCF7F89FB5D5DF58052EAEFA420";

		ArrayList<String> listLinks = new ArrayList<String>();
		ArrayList<String> listTitre = new ArrayList<String>();
		ArrayList<String> listDate = new ArrayList<String>();
		
		//for the different page
		for (int j = 0;j<pageNum;j++) {
			// define the map of the settings
			Map<String, String> params = new HashMap<String, String>();

			params.put("filtre", filtre);
			params.put("bDisplaySearchEngine", "false");
			params.put("bLaunchSearch", "true");
			params.put("sSEOperatorValue", "OR");
			params.put("filtreType", "marche.ALL_REF");
			params.put("type_avis", "tout");
			params.put("se_iMaxElementPerPage", "10");
			params.put("numPage", Integer.toString(j));
			/*
			 * 
			 * bDisplaySearchEngine: false bLaunchSearch: true sActionMarchePersonneItem:
			 * sMarchePersonneItemListMarche: sMarchePersonneItemListMarcheAll: filtre: info
			 * sSEOperatorValue: OR filtreType: marche.ALL_REF raisonSociale:
			 * iIdDepartement: type_avis: tout iIdMarcheType: se_tsStartDate: se_tsEndDate:
			 */

			String result = null;
			// send POST request to the site to get the HTML data
			try {
				result = HTTPRequest.sendPost(urlProxi, params);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println(result);

			

			String[] ListString;
			// parse the HTML data
			Document doc = Jsoup.parse(result);

			// get the links from the HTML data
			Elements eles_link1 = doc.getElementsByAttributeValueContaining("onclick",
					"javascript:window.name=\"mainWindow\";OuvrirPopup");
			ArrayList<String> listHTML = new ArrayList<String>();
			for (Element ele : eles_link1) {
				// System.out.println(ele.attr("onclick"));
				listHTML.add(ele.attr("onclick"));
			}

			// split the String to get the links
			for (String link : listHTML) {
				// System.out.println(link);
				ListString = link.split("\"");
				listLinks.add("https://www.proxilegales.fr" + ListString[3]);
			}

			// get the titre from the HTML data
			Elements eles_titre = doc.getElementsByClass("post-block");

			for (int i = 0; i < eles_titre.size(); i += 2) {
				Element ele = eles_titre.get(i);
				listTitre.add(ele.text());

			}

			// get the date from the HTML data
			for (int i = 1; i < eles_titre.size(); i += 2) {
				Element ele = eles_titre.get(i);
				listDate.add(ele.text().split("heures")[0]);
			}
		}

//		System.out.println(listLinks.size());
//		System.out.println(listTitre.size());
//		System.out.println(listDate.size());
		
		ArrayList<Avis> avisList = new ArrayList<Avis>();
		for (int i = 0; i < listLinks.size(); i++) {
			avisList.add(new Avis(listDate.get(i), listTitre.get(i), listLinks.get(i)));
		}
		
		return avisList;

	}

	/*****************************************************/

	/**
	 * Crawler for Marche-publics(info)
	 * 
	 * @author ZHI
	 * @param the loaction and the date
	 * @return list of the avis
	 * 
	 *         loaction(IDR):
	 *
	 *         <option value="1,3,7,15,26,38,42,43,63,69,73,74"
	 *         >AUVERGNE-RHÔNE-ALPES</option> <option value="1">01 - AIN</option>
	 *         <option value="3">03 - ALLIER</option> <option value="7">07 -
	 *         ARDECHE</option> <option value="15">15 - CANTAL</option>
	 *         <option value="26">26 - DROME</option> <option value="38">38 -
	 *         ISERE</option> <option value="42">42 - LOIRE</option>
	 *         <option value="43">43 - HAUTE-LOIRE</option> <option value="63">63 -
	 *         PUY-DE-DOME</option> <option value="69">69 - RHONE</option>
	 *         <option value="73">73 - SAVOIE</option> <option value="74">74 -
	 *         HAUTE-SAVOIE</option> > <option value="21,25,39,58,70,71,89,90"
	 *         >BOURGOGNE-FRANCHE-COMTÉ</option> <option value="21">21 -
	 *         COTE-D'OR</option> <option value="25">25 - DOUBS</option>
	 *         <option value="39">39 - JURA</option> <option value="58">58 -
	 *         NIEVRE</option> <option value="70">70 - HAUTE-SAONE</option>
	 *         <option value="71">71 - SAONE-ET-LOIRE</option> <option value="89">89
	 *         - YONNE</option> <option value="90">90 - BELFORT/TERRITOIRE</option>
	 *         > <option value="22,29,35,56" >BRETAGNE</option>
	 *         <option value="22">22 - COTES-D'ARMOR</option> <option value="29">29
	 *         - FINISTERE</option> <option value="35">35 - ILLE-ET-VILAINE</option>
	 *         <option value="56">56 - MORBIHAN</option> >
	 *         <option value="18,28,36,37,41,45" >CENTRE-VAL DE LOIRE</option>
	 *         <option value="18">18 - CHER</option> <option value="28">28 -
	 *         EURE-ET-LOIR</option> <option value="36">36 - INDRE</option>
	 *         <option value="37">37 - INDRE-ET-LOIRE</option> <option value="41">41
	 *         - LOIR-ET-CHER</option> <option value="45">45 - LOIRET</option> >
	 *         <option value="201,202" >CORSE</option> <option value="201">2a -
	 *         CORSE DU SUD</option> <option value="202">2b - HAUTE CORSE</option> >
	 *         <option value="971,972,973,974,975,976" >DOM</option>
	 *         <option value="971">971 - GUADELOUPE</option> <option value="972">972
	 *         - MARTINIQUE</option> <option value="973">973 - GUYANE</option>
	 *         <option value="974">974 - REUNION</option> <option value="975">975 -
	 *         ST PIERRE ET MIQUELON</option> <option value="976">976 -
	 *         MAYOTTE</option> >
	 *         <option value="8,10,51,52,54,55,57,67,68,88" >GRAND EST</option>
	 *         <option value="8">08 - ARDENNES</option> <option value="10">10 -
	 *         AUBE</option> <option value="51">51 - MARNE</option>
	 *         <option value="52">52 - HAUTE-MARNE</option> <option value="54">54 -
	 *         MEURTHE-ET-MOSELLE</option> <option value="55">55 - MEUSE</option>
	 *         <option value="57">57 - MOSELLE</option> <option value="67">67 -
	 *         BAS-RHIN</option> <option value="68">68 - HAUT-RHIN</option>
	 *         <option value="88">88 - VOSGES</option> >
	 *         <option value="2,59,60,62,80" >HAUTS-DE-FRANCE</option>
	 *         <option value="2">02 - AISNE</option> <option value="59">59 -
	 *         NORD</option> <option value="60">60 - OISE</option>
	 *         <option value="62">62 - PAS-DE-CALAIS</option> <option value="80">80
	 *         - SOMME</option> >
	 *         <option value="75,77,78,91,92,93,94,95" >ILE-DE-FRANCE</option>
	 *         <option value="75">75 - SEINE-PARIS</option> <option value="77">77 -
	 *         SEINE-ET-MARNE</option> <option value="78">78 - LES YVELINES</option>
	 *         <option value="91">91 - ESSONNE</option> <option value="92">92 -
	 *         HAUTS-DE-SEINE</option> <option value="93">93 -
	 *         SEINE-SAINT-DENIS</option> <option value="94">94 -
	 *         VAL-DE-MARNE</option> <option value="95">95 - VAL-D'OISE</option> >
	 *         <option value="14,27,50,61,76" >NORMANDIE</option>
	 *         <option value="14">14 - CALVADOS</option> <option value="27">27 -
	 *         EURE</option> <option value="50">50 - MANCHE</option>
	 *         <option value="61">61 - ORNE</option> <option value="76">76 -
	 *         SEINE-MARITIME</option> >
	 *         <option value="16,17,19,23,24,33,40,47,64,79,86,87" >NOUVELLE
	 *         AQUITAINE</option> <option value="16">16 - CHARENTE</option>
	 *         <option value="17">17 - CHARENTE-MARITIME</option>
	 *         <option value="19">19 - CORREZE</option> <option value="23">23 -
	 *         CREUSE</option> <option value="24">24 - DORDOGNE</option>
	 *         <option value="33">33 - GIRONDE</option> <option value="40">40 -
	 *         LANDES</option> <option value="47">47 - LOT-ET-GARONNE</option>
	 *         <option value="64">64 - PYRENEES-ATLANTIQUES</option>
	 *         <option value="79">79 - DEUX-SEVRES</option> <option value="86">86 -
	 *         VIENNE</option> <option value="87">87 - HAUTE VIENNE</option> >
	 *         <option value="9,11,12,30,31,32,34,46,48,65,66,81,82"
	 *         >OCCITANIE</option> <option value="9">09 - ARIEGE</option>
	 *         <option value="11">11 - AUDE</option> <option value="12">12 -
	 *         AVEYRON</option> <option value="30">30 - GARD</option>
	 *         <option value="31">31 - HAUTE-GARONNE</option> <option value="32">32
	 *         - GERS</option> <option value="34">34 - HERAULT</option>
	 *         <option value="46">46 - LOT</option> <option value="48">48 -
	 *         LOZERE</option> <option value="65">65 - HAUTES-PYRENEES</option>
	 *         <option value="66">66 - PYRENEES-ORIENTALES</option>
	 *         <option value="81">81 - TARN</option> <option value="82">82 -
	 *         TARN-ET-GARONNE</option> >
	 *         <option value="44,49,53,72,85" >PAYS-DE-LOIRE</option>
	 *         <option value="44">44 - LOIRE ATLANTIQUE</option>
	 *         <option value="49">49 - MAINE-ET-LOIRE</option> <option value="53">53
	 *         - MAYENNE</option> <option value="72">72 - SARTHE</option>
	 *         <option value="85">85 - VENDEE</option> >
	 *         <option value="4,5,6,13,83,84" >PROVENCE-ALPES-COTE D'AZUR</option>
	 *         <option value="4">04 - ALPES-DE-HAUTE-PROVENCE</option>
	 *         <option value="5">05 - HAUTES-ALPES</option> <option value="6">06 -
	 *         ALPES-MARITIMES</option> <option value="13">13 -
	 *         BOUCHES-DU-RHONE</option> <option value="83">83 - VAR</option>
	 *         <option value="84">84 - VAUCLUSE</option>
	 * 
	 * 
	 * 
	 *         datePatution: toutes : "" Aujourd'hui : "= 0" Hier : "= 1" 2 derniers
	 *         jours : "< 2" 8 derniers jours : "< 8" 30 derniers jours : "< 30"
	 */
	public ArrayList<Avis> marchepublicsInfoCrawler(String IDR, String dateParution) {
		// define the url of the site
		String urlMPI = "http://www.marches-publics.info/mpiaws/index.cfm?fuseaction=pub.affResultats&IDs=25";

		// define the map of the settings
		Map<String, String> params = new HashMap<String, String>();
		/*
		 * 
		 * IDE: EC IDN: X listeCPV: IDP: X IDR: 1,3,7,15,26,38,42,43,63,69,73,74
		 * txtLibre: txtLibreAcheteur: txtLibreVille: txtLibreLieuExec: txtLibreRef:
		 * txtLibreObjet: dateNotifDebut: dateNotifFin: txtAcheteurNom:
		 * txtAcheteurSiret: txtTitulaireNom: txtTitulaireSiret: dateParution: = 0
		 * dateExpiration: dateExpirationPassee:
		 */

		// params.put("IDE","EC");
		// params.put("IDN", "X");
		// params.put("IDP", "X");
		params.put("IDR", IDR);
		params.put("dateParution", dateParution);

		String result = null;
		// send POST request to the site to get the HTML data
		try {
			result = HTTPRequest.sendPost(urlMPI, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(result);

		// parse the HTML data
		Document doc = Jsoup.parse(result);
		ArrayList<String> listLinks = new ArrayList<String>();
		ArrayList<String> listTitre = new ArrayList<String>();
		ArrayList<String> listDate = new ArrayList<String>();

		// get the links from the HTML data
		Elements eles_links = doc.getElementsByAttributeValueStarting("href",
				"index.cfm?fuseaction=pub.affPublication");
		for (Element ele : eles_links) {
			listLinks.add("http://www.marches-publics.info/mpiaws/" + ele.attr("href"));
		}

		// get the titres from the HTML data
		Elements eles_titre0 = doc.getElementsByClass("AW_Table_Ligne0");
		Elements eles_titre1 = doc.getElementsByClass("AW_Table_Ligne1");
		for (int i = 2; i < eles_titre0.size(); i += 3) {
			Element ele0 = eles_titre0.get(i);
			listTitre.add(ele0.text());
			if (i < eles_titre1.size()) {
				Element ele1 = eles_titre1.get(i);
				listTitre.add(ele1.text());
			}
		}
//		//using LinkedHashSet to delete the repeated members
//		LinkedHashSet<String> setTitre = new LinkedHashSet<String>(listTitre);
//		listTitre = new ArrayList<String>(setTitre);
//		

		// get the date from the HTML data
		for (int i = 0; i < eles_titre0.size(); i += 3) {
			Element ele0 = eles_titre0.get(i);
			listDate.add(ele0.text());
			if (i < eles_titre1.size()) {
				Element ele1 = eles_titre1.get(i);
				listDate.add(ele1.text());
			}
		}

//		System.out.println(listLinks.size());
//		System.out.println(listTitre.size());
//		System.out.println(listDate.size());

//		System.out.println(listLinks);
//		System.out.println(listTitre);
//		System.out.println(listDate);

		ArrayList<Avis> avisList = new ArrayList<Avis>();
		for (int i = 0; i < listLinks.size(); i++) {
			avisList.add(new Avis(listDate.get(i), listTitre.get(i), listLinks.get(i)));
		}

		// for the list of the loaction
//		try {
//			result = HTTPRequest.sendGET(urlMPI);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(result);
//		Elements eles_location = doc.getElementsByAttributeValue("label", "Les régions");
//		for(Element ele : eles_location) {
//			//listLinks.add("http://www.marches-publics.info/mpiaws/"+ele.attr("href"));
//			System.out.println(ele.text());
//		}

		return avisList;
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
	 * 
	 * @author ZHI
	 * @param the location, the time from (debut) to (fin),and the number of the
	 *            pages(including the first page)
	 * @return list of the avis
	 * 
	 *         the list for the localisation:
	 * 
	 *         auvergne-rhone-alpes bourgogne-franche-comte bretagne
	 *         centre+-+val+de+loire ...
	 */
	public ArrayList<Avis> franceMarcheCrawler(String localisation, String date_parution_debut,
			String date_parution_fin, int pageNum) {
		// define the link's url to the website
		String urlFM = "https://www.francemarches.com/search?search=";

		// define the map of the parametres(settings)
		Map<String, String> params = new HashMap<String, String>();

		// define the type de presentation(domain d'activité): travaux ,
		// services,fournitures and autre
		String typeDePresentation1 = "travaux";
		String typeDePresentation2 = "services";
		String typeDePresentation3 = "fournitures";
		String typeDePresentation4 = "autre";

		params.put("typeDePresentation1", typeDePresentation1);
		params.put("typeDePresentation2", typeDePresentation2);
		params.put("typeDePresentation3", typeDePresentation3);
		params.put("typeDePresentation4", typeDePresentation4);

		// define the type de annonce(type d'avis) :AAPC, Avis d'attribution, Avis
		// rectificatif, Autre
		String typeDAnnonce1 = "avis+d%27attribution";
		String typeDAnnonce2 = "aapc";
		String typeDAnnonce3 = "avis%252Brectificatif";
		String typeDAnnonce4 = "autre";

		params.put("typeDAnnonce1", typeDAnnonce1);
		params.put("typeDAnnonce2", typeDAnnonce2);
		params.put("typeDAnnonce3", typeDAnnonce3);
		params.put("typeDAnnonce4", typeDAnnonce4);

		// define the localisation(Lieu d'exéution) 'number max' = 8 :
		// auvergne-rhone-alpes , bourgogne-franche-comte , bretagne ,
		// centre+-+val+de+loire , corse , .....
		String localisation1 = "auvergne-rhone-alpes";
		String localisation2 = "bourgogne-franche-comte";
		String localisation3 = "bretagne";
		String localisation4 = "centre+-+val+de+loire";

		params.put("localisation", localisation);
		params.put("localisation1", localisation1);
		params.put("localisation2", localisation2);
		params.put("localisation3", localisation3);
		params.put("localisation4", localisation4);

		// define "Date de publiccation"
//				String date_parution_debut = "2019-05-01";
//				String date_parution_fin = "2019-06-01";

		params.put("date_parution_debut", date_parution_debut);
		params.put("date_parution_fin", date_parution_fin);

		// define local date
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		String date_local = dateFormat.format(date).toString();

		params.put("date_local", date_local);

		// create the url by all the settings for GET request
		urlFM = "https://www.francemarches.com/search?date_parution_debut=%3E=" + params.get("date_parution_debut")
				+ "&date_parution_fin=%3C=" + params.get("date_parution_fin")
				+ "&c=q%253D%252523all%252BAND%252Bfm_class_date_cloture_dt%25253E%25253D" + params.get("date_local")
				+ "%2526b%253D0%2526s%253D%2526sl%253Dxml%2526lang%253Dfr%2526hf%253D15%2526r%253Df%25252FtypeDePrestation%25252F"
				+ params.get("typeDePresentation1") + "%2526r%253Df%25252FtypeDAnnonce%25252F"
				+ params.get("typeDAnnonce2")
				+ "%2526r%253Df%25252FsecteurDActivite%25252Fservices%252Bde%252Btechnologies%252Bde%252Bl%252527information%25252C%252Bconseil%25252C%252Bdeveloppement%252Bde%252Blogiciels%25252C%252Binternet%252Bet%252Bappui%2526r%253Df%25252F"
				+ "localisation%25252F" + params.get("localisation") + "%2526r%253Df%25252FtypeDePrestation%25252F"
				+ params.get("typeDePresentation2") + "&search=&date=&alerte_Name=";
		// System.out.println(urlFM);
		String result = null;
		try {
			result = HTTPRequest.sendGET(urlFM);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(result);

		// parse the HTML data
		Document doc = Jsoup.parse(result);
		ArrayList<String> listLinks = new ArrayList<String>();
		ArrayList<String> listTitre = new ArrayList<String>();
		ArrayList<String> listDate = new ArrayList<String>();

		Elements eles = doc.getElementsByAttributeValueStarting("href", "https://www.francemarches.com/appel-offre/");
		for (Element ele : eles) {
			listLinks.add(ele.attr("href"));
		}

		Elements eles2 = doc.getElementsByClass("dateParution");

		for (Element ele : eles2) {
			// System.out.println(ele.text());
			listDate.add(ele.text());
		}
		Elements elesTitre = doc.getElementsByClass("titre");

		for (Element ele : elesTitre) {
			// System.out.println(ele.text());
			listTitre.add(ele.text());
		}
		// using LinkedHashSet to delete the repeated members
		LinkedHashSet<String> setLinks = new LinkedHashSet<String>(listLinks);
		listLinks = new ArrayList<String>(setLinks);
		LinkedHashSet<String> setTitre = new LinkedHashSet<String>(listTitre);
		listTitre = new ArrayList<String>(setTitre);

//				System.out.println(listLinks);
//				System.out.println(listTitre);
//				System.out.println(listDate);

		// create the list fo the avis
		ArrayList<Avis> avisList = new ArrayList<Avis>();
		for (int i = 0; i < listLinks.size(); i++) {
			avisList.add(new Avis(listDate.get(i), listTitre.get(i), listLinks.get(i)));
		}

		System.out.println(avisList.size());

		/** for the others pages **/

		// define the number of the pages(including the first page)
//				int pageNum = 2;
		// for the others pages
		for (int i = 1; i < pageNum; i++) {

			// define the url of the other pages
			urlFM = "https://www.francemarches.com/search?c=q%253D%252523all%252BAND%252Bfm_class_date_cloture_dt%25253E%25253D"
					+ params.get("date_local") + "%252BAND%252Bfm_class_date_parution_dt%25253E%25253D"
					+ params.get("date_parution_debut") + "%252BAND%252Bfm_class_date_parution_dt%25253C%25253D"
					+ params.get("date_parution_fin") + "%2526b%253D" + 15 * (i - 1)
					+ "%2526s%253D%2526sl%253Dxml%2526lang%253Dfr%2526hf%253D15%2526r%253Df%25252FtypeDePrestation%25252F"
					+ params.get("typeDePresentation1") + "%2526r%253Df%25252FtypeDAnnonce%25252F"
					+ params.get("typeDAnnonce2")
					+ "%2526r%253Df%25252FsecteurDActivite%25252Fservices%252Bde%252Btechnologies%252Bde%252Bl%252527information%25252C%252Bconseil%25252C%252Bdeveloppement%252Bde%252Blogiciels%25252C%252Binternet%252Bet%252Bappui"
					+ "%2526r%253Df%25252Flocalisation%25252F" + params.get("localisation")
					+ "%2526r%253Df%25252FtypeDePrestation%25252F" + params.get("typeDePresentation2") + "&b=" + 15 * i
					+ "&s=&sa=&search=&date_parution_debut=%3E=" + params.get("date_parution_debut")
					+ "&date_parution_fin=%3C=" + params.get("date_parution_fin");

			// System.out.println(urlFM);

			try {
				result = HTTPRequest.sendGET(urlFM);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// parse the HTML data
			Document doc2 = Jsoup.parse(result);
			ArrayList<String> listLinks2 = new ArrayList<String>();
			ArrayList<String> listTitre2 = new ArrayList<String>();
			ArrayList<String> listDate2 = new ArrayList<String>();

			Elements eles3 = doc2.getElementsByAttributeValueStarting("href",
					"https://www.francemarches.com/appel-offre/");
			for (Element ele : eles3) {
				listLinks2.add(ele.attr("href"));
			}

			Elements eles4 = doc2.getElementsByClass("dateParution");

			for (Element ele : eles4) {
				// System.out.println(ele.text());
				listDate2.add(ele.text());
			}

			Elements eles5 = doc2.getElementsByClass("titre");
			for (Element ele : eles5) {
				// System.out.println(ele.text());
				listTitre2.add(ele.text());
			}
			// using LinkedHashSet to delete the repeated members
			setLinks = new LinkedHashSet<String>(listLinks2);
			listLinks2 = new ArrayList<String>(setLinks);
			setTitre = new LinkedHashSet<String>(listTitre2);
			listTitre2 = new ArrayList<String>(setTitre);

//					System.out.println(listLinks2);
//					System.out.println(listTitre2);
//					System.out.println(listDate2);
			for (int j = 0; j < listLinks2.size(); j++) {
				avisList.add(new Avis(listDate2.get(j), listTitre2.get(j), listLinks2.get(j)));
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
