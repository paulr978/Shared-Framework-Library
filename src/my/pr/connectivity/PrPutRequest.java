/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.pr.connectivity;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;

/**
 *
 * @author prando
 */
public class PrPutRequest extends PrHttpRequest {
     
    private HttpPut put = null;
    private String entityString = null;
    private boolean debug = true;
    
    public PrPutRequest(PrHttpClient client, String uri, String contentType, String entityString) throws UnsupportedEncodingException {
        this(client, uri, contentType, entityString, "UTF-8");
    }
    
    public PrPutRequest(PrHttpClient client, String uri, String contentType, byte[] entityBytes) throws UnsupportedEncodingException {
        this(client, uri, contentType, entityBytes, "UTF-8");
    }
    
    public PrPutRequest(PrHttpClient client, String uri, String contentType, File file) throws UnsupportedEncodingException {
        this(client, uri, contentType, file, "UTF-8");
    }
    
    public PrPutRequest(PrHttpClient client, String uri, String contentType, String entityString, String encodingType) throws UnsupportedEncodingException {
        super();
        this.client = client;
        
        if(uri.trim().toUpperCase().startsWith("HTTP://") || uri.trim().toUpperCase().startsWith("HTTPS://")) {
            this.url = uri.replace(" ", "%20");
        }
        else {
            this.url = client.getHostUrl() + uri.replace(" ", "%20");
        }
        
        this.entityString = entityString;
        this.put = new HttpPut(url);
        StringEntity entity = new StringEntity(this.entityString, encodingType);
        entity.setContentType(contentType);
        this.put.setEntity(entity);
        if(debug) System.out.println("Put Request: " + entityString);
        if(debug) {
            
        }
        method = put;
        
    }
    
    public PrPutRequest(PrHttpClient client, String uri, String contentType, byte[] entityBytes, String encodingType) throws UnsupportedEncodingException {
        super();
        this.client = client;
        
        if(uri.trim().toUpperCase().startsWith("HTTP://") || uri.trim().toUpperCase().startsWith("HTTPS://")) {
            this.url = uri.replace(" ", "%20");
        }
        else {
            this.url = client.getHostUrl() + uri.replace(" ", "%20");
        }
        
        //this.entityString = entityString;
        this.put = new HttpPut(url);
        
        HttpEntity entity = new ByteArrayEntity(entityBytes, ContentType.create(contentType));

        //InputStreamEntity entity = new InputStreamEntity(new ByteArrayInputStream(entityBytes), -1);
        //entity.setContentType(contentType);
        //entity.setContentEncoding(encodingType);
        //entity.setChunked(true);
        //ByteArrayEntity entity = new ByteArrayEntity(entityBytes);
        //entity.setChunked(true);
        //StringEntity entity = new StringEntity(this.entityString, encodingType);
        //entity.setContentType(contentType);
        this.put.setEntity(entity);
        if(debug) {
            System.out.println("Put Request: " + entityBytes);
        }
        method = put;
        
    }
    
    public PrPutRequest(PrHttpClient client, String uri, String contentType, File file, String encodingType) throws UnsupportedEncodingException {
        super();
        this.client = client;
        
        if(uri.trim().toUpperCase().startsWith("HTTP://") || uri.trim().toUpperCase().startsWith("HTTPS://")) {
            this.url = uri.replace(" ", "%20");
        }
        else {
            this.url = client.getHostUrl() + uri.replace(" ", "%20");
        }
        
        //this.entityString = entityString;
        this.put = new HttpPut(url);
        
        FileEntity entity = new FileEntity(file);

        this.put.setEntity(entity);
        if(debug) {
            System.out.println("Put Request, Url: " + this.url + ", File: " + file.getAbsolutePath());
        }
        method = put;
        
    }
    
    
    public PrPutRequest(PrHttpClient client, String uri) throws UnsupportedEncodingException {
        super();
        this.client = client;
        
        if(uri.trim().toUpperCase().startsWith("HTTP://") || uri.trim().toUpperCase().startsWith("HTTPS://")) {
            this.url = uri.replace(" ", "%20");
        }
        else {
            this.url = client.getHostUrl() + uri.replace(" ", "%20");
        }
        
        this.put = new HttpPut(url);

        if(debug) {
            System.out.println("Put Request, Url: " + this.url);
        }
        method = put;
        
    }

    @Override
    protected HttpResponse executeMethod(PrHttpResponse response) throws IOException {
        //System.out.println("POST Request: " + getUrl());
        //System.out.println("Data: " + this.entityString);
        HttpResponse httpResponse = client.execute(put);
        
        if(debug) {
            System.out.println(httpResponse.getStatusLine().getStatusCode());
        }
        
        return httpResponse;
    }
    
}
