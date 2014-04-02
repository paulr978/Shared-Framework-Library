/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.pr.connectivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;

/**
 *
 * @author prando
 */
public class PrGetRequest extends PrHttpRequest {
     
    private HttpGet get = null;
    private String entityString = null;
 
    public PrGetRequest(PrHttpClient client, String uri) {
        super();
        this.client = client;
        this.url = client.getHostUrl() + uri;
        this.get = new HttpGet(url);
        method = get;
        
    }

    @Override
    protected HttpResponse executeMethod(PrHttpResponse response) throws IOException {
        System.out.println("GET Request: " + getUrl());
        HttpResponse httpResponse = client.execute(get);
        
        
        return httpResponse;
    }
    
}