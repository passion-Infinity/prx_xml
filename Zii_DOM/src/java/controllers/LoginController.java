/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.XMLUtils;

/**
 *
 * @author PC
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {
    
    private static final String SUCCESS = "search.jsp";
    private static final String ERROR = "error.jsp";
    private static final String XMLFILE = "WEB-INF/students.xml";
    
    private String fullname = "";
    private boolean found = false;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String cardId = request.getParameter("txtCardID");
            String password = request.getParameter("txtPassword");
            String realPath = getServletContext().getRealPath("/");
            String filePath = realPath + XMLFILE;
            Document doc = XMLUtils.parseFileWithDOM(filePath);
            checkLogin(cardId, password, doc);
            if (found) {
                HttpSession session = request.getSession();
                session.setAttribute("FULLNAME", fullname);
                url = SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
    
    private void checkLogin(String cardId, String password, Node node) throws Exception {
        if (node == null) {
            return;
        }
        
        if (node.getNodeName().equals("student")) {
            String id = node.getAttributes().getNamedItem("cardId").getNodeValue();
            if (cardId.equals(id)) {
                NodeList children = node.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    Node tmp = children.item(i);
                    if (tmp.getNodeName().equals("password")) {
                        String pass = tmp.getTextContent().trim();
                        if (!pass.equals(password)) {
                            break;
                        }
                    } else if (tmp.getNodeName().equals("firstname")) {
                        fullname = tmp.getTextContent().trim();
                    } else if (tmp.getNodeName().equals("middlename")) {
                        fullname = fullname + " " + tmp.getTextContent().trim();
                    } else if (tmp.getNodeName().equals("lastname")) {
                        fullname = fullname + " " + tmp.getTextContent().trim();
                    } else if (tmp.getNodeName().equals("status")) {
                        String status = tmp.getTextContent().trim();
                        if (!status.equals("dropout")) {
                            found = true;
                            return;
                        }
                    }
                }
            }
        }
        
        int count = 0;
        NodeList students = node.getChildNodes();
        while (count < students.getLength()) {
            checkLogin(cardId, password, students.item(count++));
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
