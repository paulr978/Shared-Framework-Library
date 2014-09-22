/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */
package my.pr.connectivity;

import java.util.Map;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import org.jolokia.client.*;
import org.jolokia.client.request.*;


/**
 *
 * @author PRando
 */
public class PrMBeanClient {
    
    
    public PrMBeanClient() {
    
    }
    
    
    
    public static void main(String[] args) throws Exception {
        
        //JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://test70mssql:1099");
        //JMXConnector jmxConnector = JMXConnectorFactory.connect(url);
        //MBeanServerConnection mbeanServerConnection = jmxConnector.getMBeanServerConnection();
        
        JMXServiceURL u = 
 new JMXServiceURL("service:jmx:rmi://test70mssql:9999/jndi/rmi://test70mssql:1099/jmxrmi");    
JMXConnector c = JMXConnectorFactory.connect(u);

        ObjectName mbeanName = new ObjectName("java.lang:type=Memory");
        
        /*
        //J4pClient j4pClient = new J4pClient("http://test63mssql:8080/jmx-console");
        J4pClient j4pClient =  J4pClient.url("http://test63mssql:8080/jmx-console")
                               .user("admin")
                               .password("kronites")
                               .connectionTimeout(3000)
                               .build();
        
        J4pListRequest list = new J4pListRequest("java.lang");
        J4pListResponse lRes = j4pClient.execute(list);
        System.out.println(lRes);
        
        J4pReadRequest req = new J4pReadRequest("java.lang:type=Memory",
                                                "HeapMemoryUsage");
        J4pReadResponse resp = j4pClient.execute(req);
        Map<String,String> vals = resp.getValue();
        int used = Integer.parseInt(vals.get("used"));
        int max = Integer.parseInt(vals.get("max"));
        int usage = (int) (used * 100 / max);
        System.out.println("Memory usage: used: " + used + 
                           " / max: " + max + " = " + usage + "%");
        */ 
    }
    
}
