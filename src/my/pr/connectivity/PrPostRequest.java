/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */
package my.pr.connectivity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;

/**
 *
 * @author prando
 */
public class PrPostRequest extends PrHttpRequest {
     
    private HttpPost post = null;
    private String entityString = null;
    private boolean debug = false;
    
    public PrPostRequest(PrHttpClient client, String uri, String contentType, String entityString, boolean debug) throws UnsupportedEncodingException {
        this(client, uri, contentType, entityString, "UTF-8", debug);
    }
    
    public PrPostRequest(PrHttpClient client, String uri, String contentType, byte[] entityBytes, boolean debug) throws UnsupportedEncodingException {
        this(client, uri, contentType, entityBytes, "UTF-8", debug);
    }
    
    public PrPostRequest(PrHttpClient client, String uri, String contentType, String entityString, String encodingType, boolean debug) throws UnsupportedEncodingException {
        super();
        this.debug = debug;
        this.client = client;
        
        if(uri.trim().toUpperCase().startsWith("HTTP://") || uri.trim().toUpperCase().startsWith("HTTPS://")) {
            this.url = uri.replace(" ", "%20");
        }
        else {
            this.url = client.getHostUrl() + uri.replace(" ", "%20");
        }
        
        this.entityString = entityString;
        this.post = new HttpPost(url);
        StringEntity entity = new StringEntity(this.entityString, encodingType);
        entity.setContentType(contentType);
        this.post.setEntity(entity);
        if(debug) System.out.println("Post Request: " + entityString);
        if(debug) {
            
        }
        method = post;
        
    }
    
    public PrPostRequest(PrHttpClient client, String uri, String contentType, byte[] entityBytes, String encodingType, boolean debug) throws UnsupportedEncodingException {
        super();
        this.debug = debug;
        this.client = client;
        
        if(uri.trim().toUpperCase().startsWith("HTTP://") || uri.trim().toUpperCase().startsWith("HTTPS://")) {
            this.url = uri.replace(" ", "%20");
        }
        else {
            this.url = client.getHostUrl() + uri.replace(" ", "%20");
        }
        
        //this.entityString = entityString;
        this.post = new HttpPost(url);
        
        HttpEntity entity = new ByteArrayEntity(entityBytes, ContentType.create(contentType));

        //InputStreamEntity entity = new InputStreamEntity(new ByteArrayInputStream(entityBytes), -1);
        //entity.setContentType(contentType);
        //entity.setContentEncoding(encodingType);
        //entity.setChunked(true);
        //ByteArrayEntity entity = new ByteArrayEntity(entityBytes);
        //entity.setChunked(true);
        //StringEntity entity = new StringEntity(this.entityString, encodingType);
        //entity.setContentType(contentType);
        this.post.setEntity(entity);
        if(debug) {
            System.out.println("Post Request: " + entityBytes);
        }
        method = post;
        
    }

    @Override
    protected HttpResponse executeMethod(PrHttpResponse response) throws IOException {
        //System.out.println("POST Request: " + getUrl());
        //System.out.println("Data: " + this.entityString);
        HttpResponse httpResponse = client.execute(post);
        
        if(debug) {

        }
        
        return httpResponse;
    }
    
}
