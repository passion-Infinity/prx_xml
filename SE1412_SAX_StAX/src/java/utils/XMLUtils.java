/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author PC
 */
public class XMLUtils {

    public static void parseFileWithSAX(String filePath, DefaultHandler handler) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser parser = spf.newSAXParser();
        File file = new File(filePath);
        parser.parse(file, handler);
    }

    public static XMLStreamReader parseFileWithStAX(String filePath) throws Exception {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        File file = new File(filePath);
        InputStream is = new FileInputStream(file);
        XMLStreamReader reader = xif.createXMLStreamReader(is);
        return reader;
    }

    public static String getTextContent(XMLStreamReader currentCursor, String tagName)
            throws Exception {
        if (currentCursor != null) {
            while (currentCursor.hasNext()) {
                int tmp = currentCursor.next();
                if (tmp == XMLStreamConstants.START_ELEMENT) {
                    String name = currentCursor.getLocalName();
                    if (name.equals(tagName)) {
                        currentCursor.next();
                        String result = currentCursor.getText();
                        return result;
                    }
                }
            }
        }
        return null;
    }
}
