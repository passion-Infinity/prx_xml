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

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "search.jsp";
    private static final String XMLFILE = "WEB-INF/students.xml";

    private String fullname = "";
    private boolean found = false;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String realPath = getServletContext().getRealPath("/");
            String filePath = realPath + XMLFILE;
            Document doc = XMLUtils.parseFileWithDom(filePath);
            checkLogin(username, password, doc);
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

    private void checkLogin(String username, String password, Node node) throws Exception {
        if (node == null) {
            return;
        }

        if (node.getNodeName().equals("student")) {
            String id = node.getAttributes().getNamedItem("id").getNodeValue();
            if (id.equals(username)) {
                NodeList children = node.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    Node tmp = children.item(i);
                    if (tmp.getNodeName().equals("pin")) {
                        String pin = tmp.getTextContent().trim();
                        if (!password.equals(pin)) {
                            break;
                        }
                    } else if (tmp.getNodeName().equals("firstname")) {
                        fullname = tmp.getTextContent().trim();
                    } else if (tmp.getNodeName().equals("middlename")) {
                        fullname = fullname + " " + tmp.getTextContent().trim();
                    } else if (tmp.getNodeName().equals("lastname")) {
                        fullname = fullname + " " + tmp.getTextContent().trim();
                    } else if (tmp.getNodeName().equals("status")) {
                        if (!tmp.getTextContent().trim().equals("dropout")) {
                            found = true;
                            return;
                        }
                    }
                }
            }
        }

        int count = 0;
        NodeList listStudent = node.getChildNodes();
        while (count < listStudent.getLength()) {
            checkLogin(username, password, listStudent.item(count++));
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
