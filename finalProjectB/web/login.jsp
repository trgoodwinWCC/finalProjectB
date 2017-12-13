<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <form action="loginServlet">
            <span>Login</span>
            <br/><input type="text" name="UsernameLogin"/>Login Username.
            <br/><input type="text" name="PasswordLogin"/>Login Password.
            <br/><input type="submit" name="action" value="Login">
        </form>
        <hr/>
        <form action="loginServlet">
            <span>Create account</span>
            <br/><input type="text" name="UsernameLogin"/>Pick Username.
            <br/><input type="text" name="PasswordLogin"/>Pick Password.
            <br/><input type="submit" name="action" value="Login">
        </form>
    </body>
</html>