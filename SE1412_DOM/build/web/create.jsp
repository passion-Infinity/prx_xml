<%-- 
    Document   : create
    Created on : Feb 11, 2022, 7:58:11 AM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Page</title>
    </head>
    <body>
        <h1>Create New Account</h1>
        <form action="CreateController" method="post">
            ID: <input type="text" name="txtID"/> <br/>
            Class: <input type="text" name="txtClass"/> <br/>
            Address <input type="text" name="txtAddress"/> <br/>
            Lastname <input type="text" name="txtLast"/> <br/>
            Middlename <input type="text" name="txtMiddle"/> <br/>
            Firstname <input type="text" name="txtFirst"/> <br/>
            Password: <input type="text" name="txtPassword"/> <br/>
            Sex: <select name="cboSex">
                <option value="0">Male</option>
                <option value="1">Female</option>
            </select> <br/>
            <input type="submit" value="Create" />
        </form>
    </body>
</html>
