<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h2>You must login/signup</h2>
        <form action="loginServlet">
            <span>Login</span>
            <br/><input type="text" name="action" value="UsernameLogin"/>Login Username.
            <br/><input type="text" name="action" value="PasswordLogin"/>Login Password.
            <br/><input type="submit" name="action" value="Login">
        </form>
        <hr/>
        <form action="loginServlet">
            <span>Create account</span>
            <br/><input type="text" name="action" value="UsernameCreate"/>Pick Username.
            <br/><input type="text" name="action" value="PasswordCreate"/>Pick Password.
            <br/><input type="submit" name="action" value="Signup">
        </form>
    </body>
</html>