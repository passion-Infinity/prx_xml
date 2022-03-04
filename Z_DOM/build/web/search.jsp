<%-- 
    Document   : searh
    Created on : Mar 3, 2022, 9:27:19 PM
    Author     : PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <h1>${sessionScope.FULLNAME}</h1>
        <form action="SearchController" method="get">
            Address: <input  type="text" name="search"/>
            <input type="submit" value="Search"/>
        </form>
        <c:if test="${not empty requestScope.INFO}">
            <table border="1">
                <thead>
                    <tr>
                        <th>Stt</th>
                        <th>Username</th>
                        <th>Full Name</th>
                        <th>Address</th>
                        <th>Status</th>
                        <th>Sex</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${requestScope.INFO}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${dto.id}</td>
                            <td>${dto.firstname} ${dto.middlename} ${dto.lastname}</td>
                            <td>${dto.address}</td>
                            <td>${dto.status}</td>
                            <td><c:if test="${dto.sex}">Nữ</c:if><c:if test="${!dto.sex}">Nam</c:if></td>
                                <td>
                                <c:url var="deleteLink" value="DeleteController">
                                    <c:param name="username" value="${dto.id}" />
                                </c:url>
                                <a href="${deleteLink}">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>

        </c:if>
    </body>
</html>
