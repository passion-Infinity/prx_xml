/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author PC
 */
public class XMLUtils {

    //=================== StAX Cursor =====================//
    public static XMLStreamReader parseFileWithStAXCursor(String filePath) throws Exception {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        File file = new File(filePath);
        InputStream is = new FileInputStream(file);
        XMLStreamReader reader = xif.createXMLStreamReader(is);
        return reader;
    }

    public static String getTextContentByStAXCursor(XMLStreamReader currentCursor, String elementName) throws Exception {
        if (currentCursor != null) {
            while (currentCursor.hasNext()) {
                int tmp = currentCursor.next();
                if (tmp == XMLStreamConstants.START_ELEMENT) {
                    String tag = currentCursor.getLocalName();
                    if (tag.equals(elementName.trim())) {
                        currentCursor.next();
                        String result = currentCursor.getText();
                        currentCursor.nextTag();
                        return result;
                    }
                }
            }
        }
        return null;
    }

    public static String getAttributeByStAXCursor(XMLStreamReader currentCursor, String elementName, String attributeName) throws Exception {
        if (currentCursor != null) {
            while (currentCursor.hasNext()) {
                int tmp = currentCursor.next();
                if (tmp == XMLStreamConstants.START_ELEMENT) {
                    String tag = currentCursor.getLocalName();
                    if (tag.equals(elementName.trim())) {
                        String result = currentCursor.getAttributeValue("", attributeName);
                        return result;
                    }
                }
            }
        }
        return null;
    }

    //=================== StAX Iterator =====================//
    public static XMLEventReader parseFileWithStAXIterator(String filePath) throws Exception {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        File file = new File(filePath);
        InputStream is = new FileInputStream(file);
        XMLEventReader reader = xif.createXMLEventReader(is);
        return reader;
    }

    public static String getTextContentByStAXIterator(XMLEventReader reader, String elementName) throws Exception {
        if (reader != null) {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement start = event.asStartElement();
                    if (start.getName().getLocalPart().equals(elementName)) {
                        event = reader.nextEvent();
                        Characters chars = (Characters) event;
                        String value = chars.getData();
                        return value;
                    }
                }
            }
        }
        return null;
    }

    public static String getAttributeByStAXIterator(XMLEventReader reader, String elementName, String attributeName) throws Exception {
        if (reader != null) {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement start = event.asStartElement();
                    if (start.getName().getLocalPart().equals(elementName)) {
                        Attribute attr = start.getAttributeByName(new QName(attributeName));
                        if (attr != null) {
                            String value = attr.getValue();
                            return value;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    public static Map<String, String> getAttributesByStAXIterator(XMLEventReader reader, String elementName) throws Exception {
        if (reader != null) {
            Map<String, String> map = new HashMap();
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement start = event.asStartElement();
                    if (start.getName().getLocalPart().equals(elementName)) {
                        Iterator iterator =start.getAttributes();
                        while(iterator.hasNext()) {
                            Attribute attr = (Attribute) iterator.next();
                            String name = attr.getName().toString();
                            String value = attr.getValue();
                            map.put(name, value);
                        }
                        return map;
                    }
                }
            }
        }
        return null;
    }
}
