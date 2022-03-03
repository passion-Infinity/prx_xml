/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax;

import daos.NewsDAO;
import dtos.NewsDTO;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author PC
 */
public class CrawlSAX extends DefaultHandler{
    private String currentTagName;
    private NewsDTO dto;
    private NewsDAO dao = new NewsDAO();
    private boolean foundItem;

    public CrawlSAX() {
        foundItem = false;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes); //To change body of generated methods, choose Tools | Templates.
        if(qName.equals("item")) {
            dto = new NewsDTO();
            foundItem = true;
        }
        currentTagName = qName;
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length); //To change body of generated methods, choose Tools | Templates.
        String str = new String(ch, start, length);
        if(foundItem) {
            if(currentTagName.equals("title")) {
                dto.setTitle(str.trim());
            } else if(currentTagName.equals("description")) {
                dto.setDescription(str.trim());
            } else if(currentTagName.equals("link")) {
                dto.setLink(str.trim());
            } else if(currentTagName.equals("pubDate")) {
                dto.setPubDate(str.trim());
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName); //To change body of generated methods, choose Tools | Templates.
        if(qName.equals("item")) {
            try {
                foundItem = false;
                dao.insertNews(dto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        currentTagName = "";
    }

    
    
    
    
    
}
