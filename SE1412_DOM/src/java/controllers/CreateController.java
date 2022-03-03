/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import utils.XMLUtils;

/**
 *
 * @author PC
 */
public class CreateController extends HttpServlet {

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
            String status = "break";

            String realPath = getServletContext().getRealPath("/");
            String filePath = realPath + XMLFILE;
            Document doc = XMLUtils.parseFileToDOM(filePath);
            
            if(doc != null) {
                Element studentE = doc.createElement("student");
                studentE.setAttribute("id", id);
                studentE.setAttribute("class", aclass);
                
                Element firstnameE = doc.createElement("firstname");
                firstnameE.setTextContent(first);
                
                Element middlenameE = doc.createElement("middlename");
                middlenameE.setTextContent(middle);
                
                Element lastnameE = doc.createElement("lastname");
                lastnameE.setTextContent(last);
                
                Element passwordE = doc.createElement("password");
                passwordE.setTextContent(password);
                
                Element sexE = doc.createElement("sex");
                sexE.setTextContent(sex);
                
                Element addressE = doc.createElement("address");
                addressE.setTextContent(address);
                
                Element statusE = doc.createElement("status");
                statusE.setTextContent(status);
                
                studentE.appendChild(lastnameE);
                studentE.appendChild(middlenameE);
                studentE.appendChild(firstnameE);
                studentE.appendChild(sexE);
                studentE.appendChild(passwordE);
                studentE.appendChild(addressE);
                studentE.appendChild(statusE);
                
                NodeList listStudent = doc.getElementsByTagName("students");
                if(listStudent != null) {
                    listStudent.item(0).appendChild(studentE);
                    boolean result = XMLUtils.writeXML(doc, filePath);
                    if(result) {
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
