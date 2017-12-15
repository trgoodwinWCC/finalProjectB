<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <style>
        .content {
            max-width: 500px;
            margin: auto;
            padding: 5px;
        }
        </style>
    </head>
    <body>
        <div class="content">
            <h2><c:out value="${errorMessage}">You must login/signup</c:out></h2>
            <form action="loginServlet">
                <span>Login</span>
                <br/><input type="text" name="UsernameLogin"/>Login Username.
                <br/><input type="password" name="PasswordLogin"/>Login Password.
                <br/><input type="submit" name="action" value="Login">
                <div style="max-width: 280px;"><hr/></div>
                <span>Create account</span>
                <br/><input type="text" name="UsernameCreate"/>Pick Username.
                <br/><input type="password" name="PasswordCreate"/>Pick Password.
                <br/><input type="submit" name="action" value="Signup">
            </form>
        </div>
    </body>
</html>