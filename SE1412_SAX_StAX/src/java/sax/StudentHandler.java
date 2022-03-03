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

    private String username, password, fullname, currentTagName;
    private boolean foundUsername, foundPassword, found;

    public StudentHandler() {
    }

    public StudentHandler(String username, String password) {
        this.username = username;
        this.password = password;
        fullname = "";
        currentTagName = "";
        foundUsername = false;
        foundPassword = false;
        found = false;
    }

    public String getFullname() {
        return fullname;
    }

    public boolean isFound() {
        return found;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes); //To change body of generated methods, choose Tools | Templates.
        if (!found) {
            if (qName.equals("student")) {
                String id = attributes.getValue("id");
                if (id.equals(username)) {
                    foundUsername = true;
                }
            }
            currentTagName = qName;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length); //To change body of generated methods, choose Tools | Templates.
        if(!found) {
            String str = new String(ch, start, length);
            if(foundUsername) {
                if(currentTagName.equals("lastname")) {
                    fullname = str.trim();
                } else if(currentTagName.equals("middlename")) {
                    fullname = fullname + " " + str.trim();
                } else if(currentTagName.equals("firstname")) {
                    fullname = fullname + " " + str.trim();
                } else if(currentTagName.equals("password")) {
                    if(str.trim().equals(password)) {
                        foundPassword = true;
                    }
                } else if(currentTagName.equals("status")) {
                    if(foundPassword) {
                        if(!str.trim().equals("dropout")) {
                            found = true;
                        }
                    }
                }
            }
        }
    }
    
    

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName); //To change body of generated methods, choose Tools | Templates.
        if(!found) {
            currentTagName = "";
            if(qName.equals("student")) {
                foundUsername = false;
                foundPassword = false;
            }
        }
    
    }
    
    

}
