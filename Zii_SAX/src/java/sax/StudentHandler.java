/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author PC
 */
public class StudentHandler extends DefaultHandler {

    private String cardId, password, fullname, currentTagName;
    private boolean foundCardId, foundPassword, found;

    public StudentHandler() {
    }

    public StudentHandler(String cardId, String password) {
        this.cardId = cardId;
        this.password = password;
        this.fullname = "";
        this.currentTagName = "";
        this.found = false;
        this.foundCardId = false;
        this.foundPassword = false;
    }
    
    public boolean isFound() {
        return this.found;
    }
    
    public String getFullname() {
        return this.fullname;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes); //To change body of generated methods, choose Tools | Templates.
        if (!found) {
            if (qName.equals("student")) {
                String id = attributes.getValue("cardId");
                if (id.equals(this.cardId)) {
                    this.foundCardId = true;
                }
            }
            this.currentTagName = qName;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length); //To change body of generated methods, choose Tools | Templates.
        if (!found) {
            String str = new String(ch, start, length);
            if (this.foundCardId) {
                if (this.currentTagName.equals("firstname")) {
                    fullname = str.trim();
                } else if (this.currentTagName.equals("middlename")) {
                    fullname = fullname + " " + str.trim();
                } else if (this.currentTagName.equals("lastname")) {
                    fullname = fullname + " " + str.trim();
                } else if (this.currentTagName.equals("password")) {
                    if (this.password.equals(str.trim())) {
                        this.foundPassword = true;
                    }
                } else if (this.currentTagName.equals("status")) {
                    if (!str.trim().equals("dropout") && this.foundPassword) {
                        found = true;
                    }
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName); //To change body of generated methods, choose Tools | Templates.
        if (!found) {
            this.currentTagName = "";
            if (qName.equals("student")) {
                this.foundCardId = false;
                this.foundPassword = false;
            }
        }
    }

}
