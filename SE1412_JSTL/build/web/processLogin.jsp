<%-- 
    Document   : processLogin
    Created on : Feb 25, 2022, 7:30:27 AM
    Author     : PC
--%>

<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Processing...</h1>
        <c:import url="WEB-INF/accountATM.xml" var="xml_src"/>
        <x:parse var="doc" doc="${xml_src}" scope="session"/>
        
        <c:set var="username" value="${param.txtUsername}" scope="session" />
        <c:set var="password" value="${param.txtPin}"  />
        
        <c:if test="${not empty username and not empty password}">
            <x:if select="$doc//*[local-name ()='allowed' and @username = $username
                                                                                        and pin = $password]">
                <jsp:forward page="search.jsp" />
            </x:if>
        </c:if>
        <jsp:forward page="error.jsp" />
    </body>
</html>
