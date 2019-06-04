package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author ZHI
 * This class is for sending the GET request to the site 
 * with a fake header and the delay of time 
 * to pass the anti-crawler program 
 *
 */
public class SendGet {
	
	/**
	 * @param url of the site
	 * @return HTML data
	 * 
	 */
//	public static String crawler(String url) {
//		// define a string to save the result
//		  String result = "";
//		  
//		  BufferedReader in = null;
//		  try {
//		   // change the string to url
//		   URL realUrl = new URL(url);
//		   // define a connection to the url
//		   URLConnection connection = realUrl.openConnection();
//		   // start to connect
//		   connection.connect();
//		   
//		   // define the BufferedReader to get the return from connection
//		   in = new BufferedReader(new InputStreamReader(
//		     connection.getInputStream()));
//		   // for saving a line
//		   String line;
//		   while ((line = in.readLine()) != null) {
//		    // for saving every line to the result
//		    result += line;
//		   }
//		  } catch (Exception e) {
//		   System.out.println("There is the probleam to send GET request!" + e);
//		   e.printStackTrace();
//		  }
//		  // close the Buffer
//		  finally {
//		   try {
//		    if (in != null) {
//		     in.close();
//		    }
//		   } catch (Exception e2) {
//		    e2.printStackTrace();
//		   }
//		  }
//		  return result;
//	}


	public final static String getByString(String url) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        try {
            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("Accept", "text/html");
	    httpget.addHeader("Accept-Charset", "utf-8");
            httpget.addHeader("Accept-Encoding", "gzip");
	    httpget.addHeader("Accept-Language", "en-US,en");
	    httpget.addHeader("User-Agent",
			"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22");
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
 
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        System.out.println(status);
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                    	System.out.println(status);
                    	Date date=new Date();
                    	System.out.println(date);
                    	System.exit(0);
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            Thread.currentThread();
			Thread.sleep(200);
            return responseBody;
        } finally {
            httpclient.close();
        }
    }
	
//test of the function
	public static void main(String[] args) {
		String url = "https://www.francemarches.com/search?search=";
		
		//get the content of the page
		String result = null;
		try {
			result = getByString(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
	}
}
