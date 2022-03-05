/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "UpdateController", urlPatterns = {"/UpdateController"})
public class UpdateController extends HttpServlet {

    private static final String SUCCESS = "search.jsp";
    private static final String ERROR = "error.jsp";
    private static final String XMLFILE = "WEB-INF/students.xml";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String cardId = request.getParameter("txtCardId");
            String password = request.getParameter("txtPassword");
            String firstname = request.getParameter("txtFirstname");
            String middlename = request.getParameter("txtMiddlename");
            String lastname = request.getParameter("txtLastname");
            String address = request.getParameter("txtAddress");

            String realPath = getServletContext().getRealPath("/");
            String filePath = realPath + XMLFILE;
            Document doc = XMLUtils.parseFileWithDOM(filePath);

            if (doc != null) {
                String expression = "//student[@cardId='" + cardId + "']";
                XPath xPath = XMLUtils.createXPath();
                Node student = (Node) xPath.evaluate(expression, doc, XPathConstants.NODE);
                if (student != null) {
                    NodeList children = student.getChildNodes();
                    for (int i = 0; i < children.getLength(); i++) {
                        Node tmp = children.item(i);
                        if (tmp.getNodeName().equals("firstname")) {
                            tmp.setTextContent(firstname);
                        } else if (tmp.getNodeName().equals("middlename")) {
                            tmp.setTextContent(middlename);
                        } else if (tmp.getNodeName().equals("lastname")) {
                            tmp.setTextContent(lastname);
                        } else if (tmp.getNodeName().equals("address")) {
                            tmp.setTextContent(address);
                        } else if (tmp.getNodeName().equals("password")) {
                            if(!password.equals("")) {
                                tmp.setTextContent(password);
                            }
                        }
                    }
                    boolean check = XMLUtils.writeToFile(filePath, doc);
                    if (check) {
                        url = SUCCESS;
                    }
                }
            }

        } catch (Exception e) {
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
