/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dtos.Student;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.XMLUtils;

/**
 *
 * @author PC
 */
@WebServlet(name = "SearchController", urlPatterns = {"/SearchController"})
public class SearchController extends HttpServlet {
    
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "search.jsp";
    private static final String XMLFILE = "WEB-INF/students.xml";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String search = request.getParameter("search");
            String realPath = getServletContext().getRealPath("/");
            String filePath = realPath + XMLFILE;
            Document doc = XMLUtils.parseFileWithDom(filePath);
            if (doc != null) {
                String express = "//student[contains(address, '" + search + "')]";
                XPath xPath = XMLUtils.createXPath();
                NodeList students = (NodeList) xPath.evaluate(express, doc, XPathConstants.NODESET);
                if (students != null) {
                    List<Student> list = new ArrayList<>();
                    for (int i = 0; i < students.getLength(); i++) {
                        Student dto = new Student();
                        Node student = students.item(i);
                        String id = student.getAttributes().getNamedItem("id").getNodeValue();
                        dto.setId(id);
                        NodeList children = student.getChildNodes();
                        for (int j = 0; j < children.getLength(); j++) {
                            Node tmp = children.item(j);
                            if (tmp.getNodeName().equals("pin")) {
                                dto.setPin(tmp.getTextContent().trim());
                            } else if (tmp.getNodeName().equals("firstname")) {
                                dto.setFirstname(tmp.getTextContent().trim());
                            } else if (tmp.getNodeName().equals("middlename")) {
                                dto.setMiddlename(tmp.getTextContent().trim());
                            } else if (tmp.getNodeName().equals("lastname")) {
                                dto.setLastname(tmp.getTextContent().trim());
                            } else if (tmp.getNodeName().equals("status")) {
                                dto.setStatus(tmp.getTextContent().trim());
                            } else if (tmp.getNodeName().equals("address")) {
                                dto.setAddress(tmp.getTextContent().trim());
                            } else if (tmp.getNodeName().equals("sex")) {
                                if (tmp.getTextContent().trim().equals("0")) {
                                    dto.setSex(false);
                                } else {
                                    dto.setSex(true);
                                }
                            }
                        }
                        list.add(dto);
                    }
                    request.setAttribute("INFO", list);
                }
                url = SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
