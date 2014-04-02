/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.pr.connectivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;

/**
 *
 * @author prando
 */
public class PrDeleteRequest extends PrHttpRequest {
     
    private HttpDelete delete = null;
    private String entityString = null;
 
    public PrDeleteRequest(PrHttpClient client, String uri) {
        super();
        this.client = client;
        this.url = client.getHostUrl() + uri;
        this.delete = new HttpDelete(url);
        method = delete;
        
    }

    @Override
    protected HttpResponse executeMethod(PrHttpResponse response) throws IOException {
        System.out.println("Delete Request: " + getUrl());
        //System.out.println("Host Url: " + client.getHostUrl());
        HttpResponse httpResponse = client.execute(delete);
        
        
        return httpResponse;
    }
    
}
