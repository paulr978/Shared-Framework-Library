/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.pr.connectivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

/**
 *
 * @author prando
 */
public class PrPostRequest extends PrHttpRequest {
     
    private HttpPost post = null;
    private String entityString = null;
    
    public PrPostRequest(PrHttpClient client, String uri, String contentType, String entityString) throws UnsupportedEncodingException {
        this(client, uri, contentType, entityString, "UTF-8");
    }
    
    public PrPostRequest(PrHttpClient client, String uri, String contentType, String entityString, String encodingType) throws UnsupportedEncodingException {
        super();
        this.client = client;
        this.url = client.getHostUrl() + uri;
        this.entityString = entityString;
        this.post = new HttpPost(url);
        StringEntity entity = new StringEntity(this.entityString, encodingType);
        entity.setContentType(contentType);
        this.post.setEntity(entity);
        System.out.println("Post Request: " + entityString);
        method = post;
        
    }

    @Override
    protected HttpResponse executeMethod(PrHttpResponse response) throws IOException {
        //System.out.println("POST Request: " + getUrl());
        //System.out.println("Data: " + this.entityString);
        HttpResponse httpResponse = client.execute(post);
        
        
        return httpResponse;
    }
    
}
