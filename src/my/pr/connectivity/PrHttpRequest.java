/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.pr.connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 *
 * @author PRando
 */
public abstract class PrHttpRequest {
    
    protected abstract HttpResponse executeMethod(PrHttpResponse response) throws IOException;
    
    protected PrHttpClient client = null;
    protected String url = null;
    protected HttpRequestBase method = null;
    protected HttpResponse response = null;
    private long elapsedTimeMilli = -1;

    public PrHttpRequest() {

    }
    
    public String getUrl() {
        return url;
    }
    
    public PrHttpResponse execute() throws IOException {  
        PrHttpResponse response = new PrHttpResponse();
        HttpResponse httpResponse = null;
        
        long start = System.currentTimeMillis();
        
        try {
            httpResponse = executeMethod(response);
            
            long end = System.currentTimeMillis();
            this.elapsedTimeMilli = end - start;
            response.setHttpResponse(httpResponse, url, client.getCookies(), method.getAllHeaders(), elapsedTimeMilli, end);
        
        }
        catch(IOException e) {
            response.setJavaErrorException(e);
            e.printStackTrace();
            throw new IOException(e);
        }finally {
            method.releaseConnection();
        }
        
        
        //HttpResponse httpResponse = executeMethod(response);
        
        //long end = System.currentTimeMillis();
        //this.elapsedTimeMilli = end - start;
        //response.setHttpResponse(httpResponse, url, client.getCookies(), method.getAllHeaders(), elapsedTimeMilli, end);

        return response;
    }
    
}
