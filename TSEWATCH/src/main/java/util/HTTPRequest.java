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
	
	public static String sendPost(String url,Map<String,String> params) throws Exception {
		
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
		if(url == Const.BOAMP) {
			httpPost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,/;q=0.8,application/signed-exchange;v=b3");
			httpPost.setHeader("Accept-Encoding","gzip, deflate, br");
			httpPost.setHeader("Accept-Language","zh-CN,zh;q=0.9,zh-TW;q=0.8");
			httpPost.setHeader("Connection","keep-alive");
			httpPost.setHeader("Content-type","application/x-www-form-urlencoded");
			httpPost.setHeader("Host","www.boamp.fr");
			httpPost.setHeader("Referer","https://www.boamp.fr/recherche/avancee");
			httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
			
		}
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

	public static void main(String[] args) throws Exception 
	{
		/**
		 *  Test here
		 */
	}
	
	
	
	
}
