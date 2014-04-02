/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.pr.connectivity;

import java.util.concurrent.TimeUnit;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;

/**
 *
 * @author PRando
 */
public class PrHttpClientConnMgr implements ClientConnectionManager {

    @Override
    public SchemeRegistry getSchemeRegistry() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ClientConnectionRequest requestConnection(HttpRoute hr, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void releaseConnection(ManagedClientConnection mcc, long l, TimeUnit tu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void closeIdleConnections(long l, TimeUnit tu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void closeExpiredConnections() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void shutdown() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
