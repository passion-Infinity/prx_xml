<%-- 
    Document   : result
    Created on : Mar 2, 2022, 7:59:05 AM
    Author     : PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Result</h1>
        <c:if test="${not empty requestScope.INFO}">
            <table border="1">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Title</th>
                        <th>Link</th>
                        <th>Description</th>
                        <th>pubDate</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" varStatus="counter" items="${requestScope.INFO}">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${dto.title}</td>
                            <td>
                                <a href="${dto.link}">Link</a>
                            </td>
                            <td>${dto.description}</td>
                            <td>${dto.pubDate}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </body>
</html>
