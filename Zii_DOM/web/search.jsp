<%-- 
    Document   : search
    Created on : Mar 5, 2022, 10:46:39 AM
    Author     : PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <h1>${sessionScope.FULLNAME}</h1>
        <form action="SearchController" method="get">
            Name: <input type="text" name="txtSearch"/>
            <input type="submit" value="Search" />
        </form><br/>
        <c:set var="isUpdate" value="false" />
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

                                <c:url var="deleteLink" value="UpdateController">
                                    <c:param name="txtCardId" value="${dto.cardId}"/>
                                </c:url>
                                <a 
                                    style="cursor: pointer" 
                                    class="onClickUpdate"
                                    data-id="${dto.cardId}"
                                    data-password="${dto.password}"
                                    data-firstname="${dto.firstname}"
                                    data-middlename="${dto.middlename}"
                                    data-lastname="${dto.lastname}"
                                    data-address="${dto.address}"
                                    >
                                    Update
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        
        <br/>
        <br/>
        <br/>

        <form action="UpdateController" id="updateForm" method="post" hidden="true">
            <h2>Update Student</h2>
            Card Identify: <input type="text" id="txtCardId" disabled="true"/>
            <input type="hidden" name="txtCardId" id="txtId"/><br/>
            Password: <input type="password" name="txtPassword" id="txtPassword" /><br/>
            First name: <input type="text" name="txtFirstname" id="txtFirstname" /><br/>
            Middle name: <input type="text" name="txtMiddlename" id="txtMiddlename" /><br/>
            Last name: <input type="text" name="txtLastname" id="txtLastname" /><br/>
            Address: <input type="text" name="txtAddress" id="txtAddress" /><br/>
            <input type="submit" value="Update"/>
        </form>

    </body>

    <script>
        $('.onClickUpdate').click(function () {
            let cardId = $(this).data('id');
            let password = $(this).data('password');
            let firstname = $(this).data('firstname');
            let middlename = $(this).data('middlename');
            let lastname = $(this).data('lastname');
            let address = $(this).data('address');
            
            $('#txtCardId').val(cardId);
            $('#txtPassword').val(password);
            $('#txtId').val(cardId);
            $('#txtFirstname').val(firstname);
            $('#txtMiddlename').val(middlename);
            $('#txtLastname').val(lastname);
            $('#txtAddress').val(address);
            
            $('#updateForm').show();
        });
    </script>
</html>
