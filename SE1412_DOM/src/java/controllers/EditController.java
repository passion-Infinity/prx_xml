/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
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
public class EditController extends HttpServlet {

    private static final String XMLFILE = "/WEB-INF/studentAccount.xml";
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "search.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String id = request.getParameter("txtID");
            String aclass = request.getParameter("txtClass");
            String first = request.getParameter("txtFirst");
            String middle = request.getParameter("txtMiddle");
            String last = request.getParameter("txtLast");
            String password = request.getParameter("txtPassword");
            String sex = request.getParameter("cboSex");
            String address = request.getParameter("txtAddress");
            String status = request.getParameter("cboStatus");

            String realPath = getServletContext().getRealPath("/");
            String filePath = realPath + XMLFILE;
            Document doc = XMLUtils.parseFileToDOM(filePath);
            if (doc != null) {
                XPath xPath = XMLUtils.createXPath();
                String exp = "//student[@id='" + id + "']";
                Node node = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
                node.getAttributes().getNamedItem("class").setNodeValue(aclass);
                NodeList children = node.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    Node tmp = children.item(i);
                    if (tmp.getNodeName().equals("firstname")) {
                        tmp.setTextContent(first);
                    } else if (tmp.getNodeName().equals("lastname")) {
                        tmp.setTextContent(last);
                    } else if (tmp.getNodeName().equals("middlename")) {
                        tmp.setTextContent(middle);
                    } else if (tmp.getNodeName().equals("address")) {
                        tmp.setTextContent(address);
                    } else if (tmp.getNodeName().equals("password")) {
                        tmp.setTextContent(password);
                    } else if (tmp.getNodeName().equals("status")) {
                        tmp.setTextContent(status);
                    } else if (tmp.getNodeName().equals("sex")) {
                        tmp.setTextContent(sex);
                    }
                }
                boolean result = XMLUtils.writeXML(doc, filePath);
                    if(result) {
                        url = SUCCESS;
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
