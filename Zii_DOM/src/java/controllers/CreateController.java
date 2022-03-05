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
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import utils.XMLUtils;

/**
 *
 * @author PC
 */
@WebServlet(name = "CreateController", urlPatterns = {"/CreateController"})
public class CreateController extends HttpServlet {
    
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
            String sex = request.getParameter("cboSex");
            String status = request.getParameter("cboStatus");
            
            String realPath = getServletContext().getRealPath("/");
            String filePath = realPath + XMLFILE;
            Document doc = XMLUtils.parseFileWithDOM(filePath);
            
            if(doc != null) {
                Element studentE = doc.createElement("student");
                studentE.setAttribute("cardId", cardId);
                studentE.setAttribute("class", "SE");
                
                Element passwordE = doc.createElement("password");
                passwordE.setTextContent(password);
                
                Element firstnameE = doc.createElement("firstname");
                firstnameE.setTextContent(firstname);
                
                Element middlenameE = doc.createElement("middlename");
                middlenameE.setTextContent(middlename);
                
                Element lastnameE = doc.createElement("lastname");
                lastnameE.setTextContent(lastname);
                
                Element addressE = doc.createElement("address");
                addressE.setTextContent(address);
                
                Element sexE = doc.createElement("sex");
                sexE.setTextContent(sex);
                
                Element statusE = doc.createElement("status");
                statusE.setTextContent(status);
                
                studentE.appendChild(passwordE);
                studentE.appendChild(firstnameE);
                studentE.appendChild(middlenameE);
                studentE.appendChild(lastnameE);
                studentE.appendChild(addressE);
                studentE.appendChild(sexE);
                studentE.appendChild(statusE);
                
                NodeList listStudents = doc.getElementsByTagName("students");
                if(listStudents != null) {
                    listStudents.item(listStudents.getLength() - 1).appendChild(studentE);
                    boolean check = XMLUtils.writeToFile(filePath, doc);
                    if(check) {
                        url = SUCCESS;
                        HttpSession session = request.getSession();
                        session.setAttribute("FULLNAME", firstname + " " + middlename + " " + lastname);
                    }
                }
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
