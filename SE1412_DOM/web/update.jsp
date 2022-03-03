<%-- 
    Document   : create
    Created on : Feb 11, 2022, 7:58:11 AM
    Author     : PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Page</title>
    </head>
    <body>
        <h1>Create New Account</h1>
        <form action="EditController" method="post">
            ID: <input type="text" name="txtID"  value="${requestScope.STUDENT.id}"/> <br/>
            Class: <input type="text" name="txtClass" value="${requestScope.STUDENT.aclass}"/> <br/>
            Address <input type="text" name="txtAddress" value="${requestScope.STUDENT.address}"/> <br/>
            Lastname <input type="text" name="txtLast" value="${requestScope.STUDENT.lastname}"/> <br/>
            Middlename <input type="text" name="txtMiddle" value="${requestScope.STUDENT.middlename}"/> <br/>
            Firstname <input type="text" name="txtFirst" value="${requestScope.STUDENT.firstname}"/> <br/>
            Password: <input type="text" name="txtPassword" value="${requestScope.STUDENT.password}"/> <br/>
            Status <select name="cboStatus">
                <option value="${requestScope.STUDENT.status}">
                    <c:if test="${requestScope.STUDENT.status == 'break'}">
                        break
                        <c:set var = "status1"  value = "studying"/>
                        <c:set var = "status2"  value = "dropout"/>
                    </c:if>
                    <c:if test="${requestScope.STUDENT.status == 'studying'}">
                        studying
                         <c:set var = "status1"  value = "break"/>
                        <c:set var = "status2"  value = "dropout"/>
                    </c:if>
                    <c:if test="${requestScope.STUDENT.status == 'dropout'}">
                        dropout
                         <c:set var = "status1"  value = "studying"/>
                        <c:set var = "status2"  value = "break"/>
                    </c:if>
                </option>
                <option value="<c:out value="${status1}" />"><c:out value="${status1}" /></option>
                <option value="<c:out value="${status2}" />"><c:out value="${status2}" /></option>
            </select> <br/>
            Sex: <select name="cboSex">
                <option value="${requestScope.STUDENT.sex == true ? "1" : "0"}">${requestScope.STUDENT.sex == true ? "Female" : "Male"}</option>
                <option value="${requestScope.STUDENT.sex == true ? "0" : "1"}">${requestScope.STUDENT.sex == true? "Male" : "Female"}</option>
            </select> <br/>
            <input type="submit" value="Update" />
        </form>
    </body>
</html>
