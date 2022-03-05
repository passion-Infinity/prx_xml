/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sax;

import dtos.Student;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author PC
 */
public class SearchHandler extends DefaultHandler {

    private String currentTagName, search = "";
    private boolean foundSearch;
    private Student dto = null;
    private List<Student> list = null;

    public SearchHandler(String search) {
        this.currentTagName = "";
        this.search = search;
        this.foundSearch = false;
        this.list = new ArrayList<>();
    }

    public List<Student> getList() {
        return this.list;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes); //To change body of generated methods, choose Tools | Templates.
        this.currentTagName = qName;
        if (this.currentTagName.equals("student")) {
            this.dto = new Student();
            String id = attributes.getValue("cardId");
            String aclass = attributes.getValue("class");
            dto.setCardId(id);
            dto.setAclass(aclass);
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length); //To change body of generated methods, choose Tools | Templates.
        String str = new String(ch, start, length);
        if (this.currentTagName.equals("firstname")) {
            dto.setFirstname(str.trim());
        } else if (this.currentTagName.equals("middlename")) {
            dto.setMiddlename(str.trim());
        } else if (this.currentTagName.equals("lastname")) {
            dto.setLastname(str.trim());
        } else if (this.currentTagName.equals("address")) {
            if (str.trim().contains(this.search.trim())) {
                this.foundSearch = true;
            }
            dto.setAddress(str.trim());
        } else if (this.currentTagName.equals("status")) {
            dto.setStatus(str.trim());
        } else if (this.currentTagName.equals("sex")) {
            if (str.trim().equals("0")) {
                dto.setSex(false);
            } else {
                dto.setSex(true);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName); //To change body of generated methods, choose Tools | Templates.
        if (qName.equals("student") && this.foundSearch) {
            this.foundSearch = false;
            this.dto = null;
            list.add(dto);
        }
        this.currentTagName = "";
    }

}
