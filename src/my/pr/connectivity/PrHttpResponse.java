/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.pr.connectivity;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.cookie.Cookie;

/**
 *
 * @author prando
 */
public class PrHttpResponse {

    private String url = null;
    private String path = null;
    private HttpResponse response = null;
    private List<Cookie> cookies = null;
    private Header[] headers = null;
    private long elapsedTimeMilli = -1L;
    private long currentTimeMilli = -1L;
    private boolean downloaded = false;
    private byte[] bytes = null;
    private Exception javaErrorException = null;
    private boolean exceptionOccured = false;
    //private StringBuffer content = null;

    public PrHttpResponse() throws IOException {
        //content = new StringBuffer();
        cookies = new ArrayList<Cookie>();
        headers = new Header[0];
    }

    public void setHttpResponse(HttpResponse response, String url, List<Cookie> cookies, Header[] headers, long elapsedTimeMilli, long currentTimeMilli) throws IOException {
        this.response = response;
        this.url = url;
        this.cookies = cookies;
        this.headers = headers;
        this.elapsedTimeMilli = elapsedTimeMilli;
        this.currentTimeMilli = currentTimeMilli;

        if (url.startsWith("http://")) {
            path = url.replace("http://", "");
        } else if (url.startsWith("https://")) {
            path = url.replace("https://", "");
        }
        System.out.println("Response: " + response);

        if(getHttpEntity() == null) return;
        
        if (getHttpEntity().getContentLength() > 0 || isTextResponse()) {
            setByteArrayInputContent(this.response.getEntity().getContent());
        }


    }

    public boolean isTextResponse() {
        if(getContentType() == null) return false;
        
        if (getContentType().getValue().startsWith("text")) {
            return true;
        }
        else if(getContentType().getValue().contains("application/json")) {
            return true;
        }
        else if(getContentType().getValue().contains("application/soap+xml")) {
            return true;
        }
        return false;
    }

    public boolean isImageResponse() {
        if (getContentType().getValue().startsWith("image")) {
            return true;
        }
        return false;
    }

    public void saveContentToFile(File base) throws IOException {
        File file = new File(base, path);
        file.getParentFile().mkdirs();

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bos.write(bytes);
        bos.flush();
        bos.close();

    }

    public String getResponseTime() {
        return getFormattedResponseTime("yyyy/MM/dd HH:mm:ss.SSS");
    }

    public String getFormattedResponseTime(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date(currentTimeMilli);
        return dateFormat.format(date);
    }

    public String getUrl() {
        return url;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public HttpEntity getHttpEntity() {
        return response.getEntity();
    }

    public Header getContentType() {
        return getHttpEntity().getContentType();
    }

    public long getContentLength() {
        return getHttpEntity().getContentLength();
    }

    public Header[] getMethodHeaders() {
        return headers;
    }
    
    public Cookie getCookie(String name) {
        for(Cookie cookie : getCookies()) {
            if(cookie.getName().equalsIgnoreCase(name)) {
                return cookie;
            }
        }
        return null;
    }
    
    public boolean isCookieExists(String name) {
        for(Cookie cookie : getCookies()) {
            if(cookie.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public Header[] getResponseHeaders() {
        return this.response.getAllHeaders();
    }

    public Header getResponseHeader(String name) {
        for (Header header : getResponseHeaders()) {
            if (header.getName().equalsIgnoreCase(name)) {
                return header;
            }
        }
        return null;
    }

    public StatusLine getStatusLine() {
        return response.getStatusLine();
    }

    public int getStatusCode() {
        return getStatusLine().getStatusCode();
    }

    public String getReasonPhrase() {
        return getStatusLine().getReasonPhrase();
    }

    public long getElapsedTimeMilli() {
        return this.elapsedTimeMilli;
    }

    public String getStringContent() {
        if (isTextResponse()) {
            return new String(bytes);
        }
        return "";
    }

    private boolean isDownloaded() {
        return downloaded;
    }

    private void setByteArrayInputContent(InputStream input) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int read = 0;
        byte[] buffer = new byte[1024];

        while ((read = input.read(buffer)) != -1) {
            outputStream.write(buffer, 0, read);
        }
        outputStream.flush();
        bytes = outputStream.toByteArray();

        downloaded = true;
    }

    public void printReport() {
        System.out.println("Time: " + getResponseTime());
        System.out.println("Url: " + getUrl());
        
        if(getHttpEntity() == null) {
            return;
        }

        if (this.isExceptionOccured()) {
            System.out.println("Exception: " + this.getJavaErrorException().getMessage());
        } else {
            System.out.println("Status Line: " + getStatusCode() + " " + getReasonPhrase());
            System.out.println(getStringContent());
            System.out.println("Elapsed Time(ms): " + getElapsedTimeMilli());

            //System.out.println("Content Encoding: " + get.getHttpEntity().getContentEncoding().getName() + "=" + get.getHttpEntity().getContentEncoding().getValue());
            System.out.println("Content Type: " + getContentType().getName() + "=" + getContentType().getValue());
            System.out.println("Content Length: " + getContentLength());

            for (Cookie cookie : cookies) {
                System.out.println("Cookie: " + cookie.getName() + "=" + cookie.getValue());
            }
            
            for(Header header: getResponseHeaders()) {
                System.out.println("Response Header: " + header.getName() + "=" + header.getValue());
            }
            
            for(Header header: getMethodHeaders()) {
                System.out.println("Method Header: " + header.getName() + "=" + header.getValue());
            }
        }


    }

    /**
     * @return the javaErrorException
     */
    public Exception getJavaErrorException() {
        return javaErrorException;
    }

    /**
     * @param javaErrorException the javaErrorException to set
     */
    public void setJavaErrorException(Exception javaErrorException) {
        this.javaErrorException = javaErrorException;
        exceptionOccured = true;
    }

    /**
     * @return the exceptionOccured
     */
    public boolean isExceptionOccured() {
        return exceptionOccured;
    }
}
