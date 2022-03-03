<%-- 
    Document   : search
    Created on : Feb 7, 2022, 8:20:47 AM
    Author     : PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body>
        <h1>Hello ${sessionScope.FULLNAME}!</h1>
        <form action="SearchController" method="post">
            Address: <input type="text" name="txtSearch" /> <br/>
            <input type="submit" value="Search" />
        </form>
        <c:if test="${not empty requestScope.INFO}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>ID</th>
                        <th>Class</th>
                        <th>Fullname</th>
                        <th>Address</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.INFO}" var="dto" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${dto.id}</td>
                            <td>${dto.aclass}</td>
                            <td>${dto.lastname} ${dto.middlename} ${dto.firstname}</td>
                            <td>${dto.address}</td>
                            <td>
                                <c:url var="deleteLink" value="DeleteController">
                                    <c:param name="txtID" value="${dto.id}"/>
                                </c:url>
                                <a href="${deleteLink}">Delete</a>
                                
                                <c:url var="updateLink" value="UpdateController">
                                    <c:param name="txtID" value="${dto.id}" />
                                    <c:param name="txtClass" value="${dto.aclass}" />
                                    <c:param name="txtFirst" value="${dto.firstname}" />
                                    <c:param name="txtMiddle" value="${dto.middlename}" />
                                    <c:param name="txtLast" value="${dto.lastname}" />
                                    <c:param name="txtPassword" value="${dto.password}" />
                                    <c:param name="txtAddress" value="${dto.address}" />
                                    <c:param name="txtStatus" value="${dto.status}" />
                                    <c:param name="txtSex" value="${dto.sex}" />
                                </c:url>
                                <a href="${updateLink}">Update</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </body>
</html>
