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
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.XMLEvent;
import utils.XMLUtils;

/**
 *
 * @author PC
 */
@WebServlet(name = "SearchController", urlPatterns = {"/SearchController"})
public class SearchController extends HttpServlet {

    private static final String SUCCESS = "search.jsp";
    private static final String ERROR = "error.jsp";
    private static final String XMLFILE = "WEB-INF/students.xml";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        List<Student> students = null;
        try {
            String search = request.getParameter("txtSearch");

            String realPath = getServletContext().getRealPath("/");
            String filePath = realPath + XMLFILE;
            XMLEventReader reader = XMLUtils.parseFileWithStAXIterator(filePath);

            while (reader.hasNext()) {
                reader.nextEvent();
                String id = null;
                String aclass = null;
                Map<String, String> attrs = XMLUtils.getAttributesByStAXIterator(reader, "student");
                if (attrs != null) {
                    for (Map.Entry<String, String> entry : attrs.entrySet()) {
                        if (entry.getKey().equals("cardId")) {
                            id = entry.getValue();
                        } else if (entry.getKey().equals("class")) {
                            aclass = entry.getValue();
                        }
                    }
                }
                if (id != null) {
                    String firstname = XMLUtils.getTextContentByStAXIterator(reader, "firstname");
                    String middlename = XMLUtils.getTextContentByStAXIterator(reader, "middlename");
                    String lastname = XMLUtils.getTextContentByStAXIterator(reader, "lastname");
                    String address = XMLUtils.getTextContentByStAXIterator(reader, "address");
                    String sex = XMLUtils.getTextContentByStAXIterator(reader, "sex");
                    String status = XMLUtils.getTextContentByStAXIterator(reader, "status");

                    if (address.contains(search)) {
                        Student dto = new Student();
                        dto.setCardId(id.trim());
                        dto.setAclass(aclass.trim());
                        dto.setFirstname(firstname.trim());
                        dto.setMiddlename(middlename.trim());
                        dto.setLastname(lastname.trim());
                        dto.setAddress(address.trim());
                        dto.setStatus(status.trim());
                        if ("1".equals(sex.trim())) {
                            dto.setSex(true);
                        } else {
                            dto.setSex(false);
                        }

                        if (students == null) {
                            students = new ArrayList<>();
                        }

                        students.add(dto);
                    }
                }
            }
            request.setAttribute("INFO", students);
            url = SUCCESS;
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
