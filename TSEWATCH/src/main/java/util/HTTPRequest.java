package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Model.AvisST;

public class HTTPRequest {
	
	 
	private static int downloadNum = 0;
	
	private static int crawlUrlNum = 0;
	
	private static ArrayList<String> listBoampNameOfPDF = null;
	
	private static ArrayList<AvisST> listAvisST = null;
	
	private static String USER_AGENT = "Mozilla/5.0"; 
	
	
	
    public static void incrementDownloadNumByOne() {
    	downloadNum ++;
    }
    
    public static void incrementCrawlUrlNumByOne() {
    	crawlUrlNum ++;
    }
	 
    public static void resetDownloadNum() {
    	if(downloadNum != 0)
    		System.out.println("Finished! " + downloadNum + " files downloaded....");
    	else
    		System.out.println("Starting downloading.....");
    	downloadNum = 0;
    	
    }
    private static void resetCrawlUrlNum(){
    	if(crawlUrlNum != 0)
    		System.out.println("Finished! " + crawlUrlNum + " urls(useful) crawled....");
    	else
    		System.out.println("Starting crawling.....");
    	crawlUrlNum = 0;
    }
	public static int getDownloadNum() {
		return downloadNum;
	}

	public static void setDownloadNum(int downloadNum) {
		HTTPRequest.downloadNum = downloadNum;
	}

	public static void disableCertificateValidation() {
		    // Create a trust manager that does not validate certificate chains
		    TrustManager[] trustAllCerts = new TrustManager[] { 
		    		new X509TrustManager() {
						public void checkClientTrusted(X509Certificate[] arg0, String arg1)
								throws CertificateException {
						}
						public void checkServerTrusted(X509Certificate[] arg0, String arg1)
								throws CertificateException {
							
						}
						public X509Certificate[] getAcceptedIssuers() {
							return null;
						}
		    			
		    		}
		    };
		    // Ignore differences between given hostname and certificate hostname
		    HostnameVerifier hv = new HostnameVerifier() {
		      public boolean verify(String hostname, SSLSession session) { return true; }
		    };
		    // Install the all-trusting trust manager
		    try {
		      SSLContext sc = SSLContext.getInstance("SSL");
		      sc.init(null, trustAllCerts, new SecureRandom());
		      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		      HttpsURLConnection.setDefaultHostnameVerifier(hv);
		    } catch (Exception e) {}
		  }
	
	public String sendPost(String url,Map<String,String> params) throws Exception {
		
		disableCertificateValidation();
		// Get a httpClient object
		CloseableHttpClient httpclient = HttpClients.createDefault();
	    
		// Creat a list to store params
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		for(Map.Entry<String, String> entry : params.entrySet()) {
			formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
		// Generate a post request
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;
		
		try {
			response = httpclient.execute(httpPost);
		} catch (ClientProtocolException e) { 
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HttpEntity entity1 = response.getEntity();
		String result = null;
		try {
			result = EntityUtils.toString(entity1);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// Scrawler ONLY for https://boamp.fr/avis/liste
	public ArrayList<String> getLinksBOAMP() throws Exception{
		listBoampNameOfPDF = new ArrayList<String>();
		listBoampNameOfPDF.clear();
		String AuthFileName = this.getClass().getClassLoader().getResource("jssecacerts").getPath();
		System.setProperty("javax.net.ssl.trustStore",AuthFileName);
		String url = "https://www.boamp.fr/avis/liste";
		Map<String,String> params = new HashMap<String, String>();
		
		//params.put(Const.CONF1,"0");
		//params.put(Const.CONF2,"0");
		
		
		for(String str : Const.listDescripteur) {
			params.put(Const.DESCRIPTION, str);
		}
		
		// 5 -> Cover all 1-4 options
		params.put(Const.AVIS, "5");
		
		
		// Get result
		String result = sendPost(url, params);
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
            listBoampNameOfPDF.add(obj.toString().split("/")[length-2]);
        }
        return listLinks;
	}
	
	// get links from https://www.saint-etienne.fr/node/5659
	private ArrayList<String> getLinksST(String dateParution/*params add here*/) throws Exception{
		String url = "https://cg42.marches-publics.info/avis/index.cfm?fuseaction=pub.affResultats&IDs=11";
		Map<String,String> params = new HashMap<String, String>();
		params.put("dateParution", dateParution);
		
		// Get result
		String result = sendPost(url, params);
		Document doc = Jsoup.parse(result);
		
		ArrayList<String> listLinks = new ArrayList<String>();
		
		Elements eles = doc.getElementsByAttributeValueStarting("href", "index.cfm?fuseaction=pub.affPublication&refPub=");
		for(Element ele : eles) {
			listLinks.add("https://cg42.marches-publics.info/avis/" + ele.attr("href"));
		}
		return listLinks;
	}
	
	
	public static String getHTML(String urlToRead) throws Exception {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(urlToRead);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      return result.toString();
	   }
	

	private static AvisST getInfoAvisSt(String url) throws Exception {
		incrementCrawlUrlNumByOne();
		Document doc = Jsoup.parse(getHTML(url));
		/**
		 * find data one by one
		 */
		Elements elesTop = doc.getElementsByAttributeValue("valign", "top");
//		private String title;
		Elements topBTags = elesTop.get(0).getElementsByTag("b");
		String title = topBTags.get(0).text();		
		String[] elesBrTop = elesTop.get(0).html().toString().split("<br>");
//		private String nameOfPoster;
		String nameOfPoster = elesBrTop[1].trim();
//		private String address;
		String address = elesBrTop[2].trim() + " " + elesBrTop[3].trim();
//		private String phoneNum;
		
		String phoneNum = Regex.telNumber(elesBrTop[4]).trim();
		if(null == phoneNum) phoneNum = "unknown";
		
//		private String reference;
		Elements elesTable = doc.getElementsByClass("AW_TableM_Bloc1_Clair");
		String reference = elesTable.get(1).text();
//		private String typeOfMarket;
		String typeOfMarket = elesTable.get(3).text();
//		private String description;
		String description = elesTable.get(9).text();
//		private String deadline;
		String deadline = Regex.deadline(getHTML(url)).replaceAll("</b>", "").replaceAll("<br />", "").replaceAll("</td>", "");
		return new AvisST(title,nameOfPoster,address,phoneNum,reference,typeOfMarket,description,deadline);
	}
	// Download Function
	// Param : Url to download
	private static void download(String url,String nameOfFile)throws MalformedURLException {
		URL site = new URL(url);
		Calendar calendar = Calendar.getInstance();
		Date time = calendar.getTime();
		int lengthFolderName = time.toString().replaceAll(" ", "").length();
		String folderName = time.toString().replaceAll(" ", "").substring(3, lengthFolderName-9);
		folderName = folderName.substring(0, 5) + "_" + folderName.substring(5).replaceAll(":", "-");
		try {
			File directory = new File(String.valueOf("./Boamp/" + folderName + "/"));
			if(!directory.exists()) {
				directory.mkdir();
			}
			FileOutputStream fos = new FileOutputStream(new File("./Boamp/" + folderName + "/" +  nameOfFile + ".pdf"));
			InputStream in = site.openStream();
			//System.out.println("reading from resource and writing to file...");
			int length = -1;
			byte[] buffer = new byte[1024];
			while((length=in.read(buffer)) > -1) {
				fos.write(buffer,0,length);
			}
			
			fos.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		incrementDownloadNumByOne();
	}
	
	// An integrated function for crawling url
	// => https://boamp.fr/avis/liste/
	public void boampScrawler() throws Exception {
		ArrayList<String> links = getLinksBOAMP();
		System.out.println("Links(" + links.size() + ") correspond a PDF finded!!!!!");
		
	}
	
	// An integrated function for crawling url
	// => https://saint-etienne.fr/node/5659/
	public void stScrawler() throws Exception {
		listAvisST = new ArrayList<AvisST>();
		ArrayList<String> links = getLinksST("< 8");
		resetCrawlUrlNum();
		for(String link : links) {
			listAvisST.add(getInfoAvisSt(link));
		}
		resetCrawlUrlNum();
	}
	
	public static void main(String[] args) throws Exception 
	{
		HTTPRequest http = new HTTPRequest();
		http.boampScrawler();
		
		//stScrawler();
		
		
	}
	
	
	
	
}
