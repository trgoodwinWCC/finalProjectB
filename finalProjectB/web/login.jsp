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
        <form action="loginServletT">
            <span>Login</span>
            <br/><input type="text" name="UsernameLogin"/>Login Username.
            <br/><input type="password" name="PasswordLogin"/>Login Password.
            <br/><input type="submit" name="action" value="Login">
        </form>
        <hr/>
        <form action="loginServletT">
            <span>Create account</span>
            <br/><input type="text" name="UsernameCreate"/>Pick Username.
            <br/><input type="password" name="PasswordCreate"/>Pick Password.
            <br/><input type="submit" name="action" value="Signup">
        </form>
    </body>
</html>