/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxb;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author PC
 */
public class JAXBUnmarshall {

    public static void main(String[] args) {
        try {
//            JAXBContext jc = JAXBContext.newInstance(Customer.class);
//            Unmarshaller um = jc.createUnmarshaller();
//            File f = new File("customers.xml");
//            Customer cus = (Customer)um.unmarshal(f);
//            System.out.println("ID: " + cus.getCustomerid());

            JAXBContext jc = JAXBContext.newInstance(Customers.class);
            Unmarshaller um = jc.createUnmarshaller();
            File f = new File("customers.xml");
            Customers list = (Customers) um.unmarshal(f);
            for (Customer cus : list.getCustomer()) {
                System.out.println("ID: " + cus.getCustomerid());
                System.out.println("Name: " + cus.getName());
                System.out.println("Phone: " + cus.getPhone());
                System.out.println("-----");
            }

        } catch (Exception e) {
        }
    }
}
