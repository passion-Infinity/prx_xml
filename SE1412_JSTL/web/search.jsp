<%-- 
    Document   : search
    Created on : Feb 25, 2022, 7:41:40 AM
    Author     : PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <x:set var="currentAccount" select="$doc//*[@username=$username]" />
        <h2>Welcome,  <x:out select="$currentAccount/fullname" /></h2>
        <h3>Your balance,  <x:out select="$currentAccount/@balance" /></h3>
        <h1>Transaction list</h1>
        <form action="search.jsp">
            From Date: <input type="text" name="txtFromDate"  value="${param.txtFromDate}"/> (yyyy/mm/dd) <br/>
            To Date: <input type="text" name="txtToDate"  value="${param.txtToDate}"/> (yyyy/mm/dd) <br/>
            <input  type="submit" value="List"/>
        </form>
            
        <c:set var="fromDate" value="${param.txtFromDate}"/>
        <c:set var="toDate" value="${param.txtToDate}"/>
        
        <c:set var="fromDate" value="${fn:replace(fromDate, '/', '')}" />
        <c:set var="toDate" value="${fn:replace(toDate, '/', '')}" />
        <x:set var="transaction"
               select="$currentAccount//transaction[translate(date,'/','') >= $fromDate and translate(date,'/','') <= $toDate]" />
        <x:if select="$transaction">
            <table border="1">
                <thead>
                    <tr>
                        <th>stt</th>
                        <th>date</th>
                        <th>amount</th>
                        <th>transaction</th>
                    </tr>
                </thead>
                <tbody>
                    <x:forEach var="trans" select="$transaction" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td><x:out select="$trans/date" /></td>
                            <td><x:out select="$trans/amount" /></td>
                            <td>
                                <x:choose>
                                    <x:when select="$trans[type=0]">Withdrawn</x:when>
                                    <x:when select="$trans[type=1]">Deposit</x:when>
                                    <x:when select="$trans[type=2]">Transfer</x:when>
                                    <x:otherwise>Your account is hacked</x:otherwise>
                                </x:choose>
                            </td>
                        </tr>
                    </x:forEach>
                </tbody>
            </table>
        </x:if>
    </body>
</html>
