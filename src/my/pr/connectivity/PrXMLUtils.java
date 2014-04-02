/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.pr.connectivity;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author prando
 */
public class PrXMLUtils {

    public static void parseString(String xml, DefaultHandler handler) throws IOException {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            InputSource is = new InputSource(new StringReader(xml));
            saxParser.parse(is, handler);
            
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new IOException("Exception Encountered!");
        } catch (SAXException e) {
            e.printStackTrace();
            throw new IOException("Exception Encountered!");
        }

    }
}
