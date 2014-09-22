/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.pr.connectivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthProtocolState;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.auth.NTLMEngine;
import org.apache.http.impl.auth.NTLMScheme;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

/**
 *
 * @author PRando
 */
public class PrHttpClient {

    private static final int DEFAULT_SOCKET_TIMEOUT = 5000;
    private static final int DEFAULT_CONN_TIMEOUT = 5000;
    private static final int DEFAULT_CONN_MGR_TIMEOUT = 5000;
    private DefaultHttpClient client = null;
    private String hostUrl = null;
    private LinkedList<PrHttpResponse> responses = null;
    
    private boolean debug = false;

    public PrHttpClient(String hostUrl) {
        super();
        PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();
        client = new DefaultHttpClient(connectionManager);
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, DEFAULT_CONN_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, DEFAULT_SOCKET_TIMEOUT);

        new PrHttpClientConnMgr();
        
        this.hostUrl = hostUrl;
        this.hostUrl = this.hostUrl.replace(" ", "%20");

        responses = new LinkedList<PrHttpResponse>();

    }

    public PrHttpClient(String hostUrl, int connTimeout, int socketTimeout) {
        this(hostUrl, connTimeout, socketTimeout, false);
    }
    
    public PrHttpClient(String hostUrl, int connTimeout, int socketTimeout, boolean debug) {
        super();
        this.debug = debug;
        PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();
        client = new DefaultHttpClient(connectionManager);
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, connTimeout);
        HttpConnectionParams.setSoTimeout(params, socketTimeout);

        new PrHttpClientConnMgr();

        this.hostUrl = hostUrl;
        responses = new LinkedList<PrHttpResponse>();

    }

    private void setRequestInterceptors() {
        client.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(
                    final HttpRequest request,
                    final HttpContext context)
                    throws HttpException, IOException {

                // Start Debug
                System.out.println("*** Request headers ***");
                Header[] requestHeaders = request.getAllHeaders();
                for (Header header : requestHeaders) {
                    System.out.println(header.toString());
                }
                System.out.println("***********************");
                // End Debug
            }
        });
    }
    
    private void setResponseInterceptors() {
        client.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(
                    final HttpResponse response,
                    final HttpContext context)
                    throws HttpException, IOException {

                // Start Debug
                System.out.println("** Response headers ***");
                System.out.println("Status Code: " + response.getStatusLine().getStatusCode());
                Header[] responseHeaders = response.getAllHeaders();
                for (Header header : responseHeaders) {
                    System.out.println(header.toString());
                }
                System.out.println("***********************");
                // End Debug
            }
        });
    }
    
    public void addPreemptiveAuthInterceptor() {
        client.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(
                    final HttpRequest request,
                    final HttpContext context)
                    throws HttpException, IOException {

                //request.addHeader(new NTLMScheme()));
                // End Debug
            }
        });

    }

    public void setNTCredentials(String username, String password, String domain) {
        setNTCredentials(username, password, domain, domain);
    }

    public void setNTCredentials(String username, String password, String userDomain, String hostDomain) {
        NTCredentials creds = new NTCredentials(username, password, userDomain, hostDomain);
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, creds);
        setCredentialsProvider(credsProvider);
    }
    
    public void setNTCredentials(String username, String password) {
        NTCredentials creds = new NTCredentials(username + ":" + password);
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, creds);
        setCredentialsProvider(credsProvider);
    }

    public void setCredentialsProvider(CredentialsProvider provider) {
        client.setCredentialsProvider(provider);
    }
    
    public void enableIntegratedWindowsCredentialsProvider() {

    }

    public PrHttpResponse getLastHttpResponse() {
        return responses.getLast();
    }

    public HttpClient getHttpClient() {
        return client;
    }

    public HttpResponse execute(HttpUriRequest uriRequest) throws IOException {
        /*
         try {
         HttpResponse response = client.execute(uriRequest);
         return response;
         }
         catch(IOException e) {
         e.printStackTrace();
         System.out.println("IOException Caught");
         throw e;
         }
         catch(Exception e) {
         e.printStackTrace();
         System.out.println("Exception Caught");
         throw new IOException(e);
         }
         */
        HttpResponse response = client.execute(uriRequest);
        return response;

    }

    public File processFileRequest(String uri, String fileName) throws IOException {
        String u = getHostUrl() + uri;
        //System.out.println(u);
        URL url = new URL(u);
        URLConnection urlConnection = url.openConnection();

        File file = new File(fileName);
        InputStream input = urlConnection.getInputStream();
        byte[] buffer = new byte[4096];
        int n = -1;

        OutputStream output = new FileOutputStream(file);
        while ((n = input.read(buffer)) != -1) {
            if (n > 0) {
                output.write(buffer, 0, n);
            }
        }
        output.close();
        //JarURLConnection jarConnection = (JarURLConnection) urlConnection;
        //HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;

        return file;
        //JarFile jar = jarConnection.getJarFile();
        //return jar;
    }

    public JarFile processJarRequest(String uri) throws IOException {
        String u = getHostUrl() + uri;
        //System.out.println(u);
        URL url = new URL("jar:" + u + "!/");
        URLConnection urlConnection = url.openConnection();

        JarURLConnection jarConnection = (JarURLConnection) urlConnection;
        //HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;

        JarFile jar = jarConnection.getJarFile();
        return jar;
    }

    public PrHttpResponse processGetRequest(String uri) throws IOException {
        return processGetRequest(uri, null);
    }

    public PrHttpResponse processGetRequest(String uri, String queryString) throws IOException {
        String modUri = uri;
        if (queryString != null) {
            modUri += queryString;
        }

        PrGetRequest get = new PrGetRequest(this, modUri);
        PrHttpResponse response = get.execute();
        responses.add(response);
        return response;
    }

    public PrHttpResponse processPostSoap(String uri, String soapEntity) throws IOException {
        return processPostRequest(uri, "application/soap+xml", soapEntity);
    }

    public PrHttpResponse processPostSoap(String uri, byte[] soapEntity) throws IOException {
        return processPostRequest(uri, "application/soap+xml", soapEntity);
    }

    public PrHttpResponse processPostXml(String uri, String xmlEntity) throws IOException {
        return processPostRequest(uri, "text/xml", xmlEntity);
    }

    public PrHttpResponse processPostJSON(String uri, String jsonEntity) throws IOException {
        return processPostRequest(uri, "application/json", jsonEntity);
    }

    public PrHttpResponse processDeleteRequest(String uri) throws IOException {
        PrDeleteRequest del = new PrDeleteRequest(this, uri);
        PrHttpResponse response = del.execute();
        responses.add(response);

        return response;
    }
    
    public PrHttpResponse processPutRequest(String uri) throws IOException {
        PrPutRequest put = new PrPutRequest(this, uri);
        PrHttpResponse response = put.execute();
        responses.add(response);

        return response;
    }
    
    public PrHttpResponse processPutRequest(String uri, File file) throws IOException {
        PrPutRequest put = new PrPutRequest(this, uri, "", file);
        PrHttpResponse response = put.execute();
        responses.add(response);

        return response;
    }
    
    public PrHttpResponse processPostRequest(String uri, String contentType, String stringEntity) throws IOException {
        return processPostRequest(uri, contentType, stringEntity, false);
    }

    public PrHttpResponse processPostRequest(String uri, String contentType, byte[] entityBytes) throws IOException {
        return processPostRequest(uri, contentType, entityBytes, false);
    }

    public PrHttpResponse processPostRequest(String uri, String contentType, String stringEntity, boolean debug) throws IOException {
        if(debug) System.out.println("POST REQ: " + stringEntity);
        PrPostRequest post = new PrPostRequest(this, uri, contentType, stringEntity, debug);
        PrHttpResponse response = post.execute();
        responses.add(response);

        return response;
    }

    public PrHttpResponse processPostRequest(String uri, String contentType, byte[] entityBytes, boolean debug) throws IOException {
        PrPostRequest post = new PrPostRequest(this, uri, contentType, entityBytes, debug);
        PrHttpResponse response = post.execute();
        responses.add(response);

        return response;
    }

    public List<PrHttpResponse> getResponses() {
        return responses;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public List<Cookie> getCookies() {
        return client.getCookieStore().getCookies();
    }

    public static void main(String[] args) throws Exception {
        //KGSHttpClient client = new KGSHttpClient("http://192.168.1.100/1");

        PrHttpClient client = new PrHttpClient("https://kch.kronos.com");
        PrHttpResponse response = client.processGetRequest("/sites/Cloud");
        
        response.printReport();
        
        /*
        PrHttpClient client = new PrHttpClient("http://server-a");
        JarFile jar = client.processJarRequest("/test.jar");
        Attributes attr = jar.getManifest().getMainAttributes();
        Set a = attr.keySet();
        Iterator i = a.iterator();
        while (i.hasNext()) {
            java.util.jar.Attributes.Name key = (java.util.jar.Attributes.Name) i.next();
            String value = attr.getValue(key);
            System.out.println(key + " " + value);
        }
        */ 



    }
}
