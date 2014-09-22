/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
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
    private boolean debug = true;
 
    public PrGetRequest(PrHttpClient client, String uri) {
        super();
        this.client = client;
        if(uri.trim().toUpperCase().startsWith("HTTP://") || uri.trim().toUpperCase().startsWith("HTTPS://")) {
            this.url = uri.replace(" ", "%20");
        }
        else {
            this.url = client.getHostUrl() + uri.replace(" ", "%20");
        }
        
        this.get = new HttpGet(url);
        method = get;
        
    }

    @Override
    protected HttpResponse executeMethod(PrHttpResponse response) throws IOException {
        if(debug) System.out.println("GET Request: " + getUrl());
        HttpResponse httpResponse = client.execute(get);
        
        
        return httpResponse;
    }
    
}
