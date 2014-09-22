/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */
package my.pr.utils;

import sun.net.www.http.HttpClient;

/**
 *
 * @author prando
 */
public class OHttpClient {
	private HttpClient client = null;
	private String url = null;
	
        /*
	public OHttpClient(String url) {
		this.url = url;
		client = new HttpClient();
		
	}
	
	public String get() {
		String response = null;
		GetMethod method = new GetMethod(url);
	    try {
	        // Execute the method.
	        int statusCode = client.executeMethod(method);

	        if (statusCode != HttpStatus.SC_OK) {
	          System.err.println("Method failed: " + method.getStatusLine());
	        }

	        // Read the response body.
	        byte[] responseBody = method.getResponseBody();
	        response = new String(responseBody);
	        // Deal with the response.
	        // Use caution: ensure correct character encoding and is not binary data
	        System.out.println(new String(responseBody));

	      } catch (HttpException e) {
	        System.err.println("Fatal protocol violation: " + e.getMessage());
	        e.printStackTrace();
	      } catch (IOException e) {
	        System.err.println("Fatal transport error: " + e.getMessage());
	        e.printStackTrace();
	      } finally {
	        // Release the connection.
	        method.releaseConnection();
	      }  
	      
	      return response;
	}  
        * 
        */
}
