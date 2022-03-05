<%-- 
    Document   : search.jsp
    Created on : Mar 5, 2022, 6:02:47 PM
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
            Address: <input type="text" name="txtSearch"/>
            <input type="submit" value="Search" />
        </form><br/>
        <c:if test="${not empty requestScope.INFO}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Card Id</th>
                        <th>Full Name</th>
                        <th>Address</th>
                        <th>Sex</th>
                        <th>Status</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.INFO}" var="dto" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td id="cardId">${dto.cardId}</td>
                            <td>${dto.firstname} ${dto.middlename} ${dto.lastname}</td>
                            <td>${dto.address}</td>
                            <td><c:if test="${dto.sex == true}">Nữ</c:if><c:if test="${dto.sex == false}">Nam</c:if></td>
                            <td>${dto.status}</td>
                            <td>
                                <c:url var="deleteLink" value="DeleteController">
                                    <c:param name="txtCardId" value="${dto.cardId}"/>
                                    <c:param name="txtSearch" value="" />
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
