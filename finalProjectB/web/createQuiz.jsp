<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Quiz Page</title>
        <style>
            body {
                background-color: <c:out value="${backgroundColor}">peach</c:out>;
            }
            .login {
                background-color:lightgrey;
                margin-right: 40px;
                float: right;
            }
        </style>
    </head>
    <body>
        <c:if test="${!empty Username}">
            <span class="login">User <span style="font-size:large;color:blue"><c:out value="${Username}"></c:out></span> is logged in. <a href='loginServlet?action=Logout'>Logout</a></span>
        </c:if>
        <h2>Quiz creation start</h2>
        <br/>
        <h2><c:out value="${error}"></c:out></h2>
        <br/>
        <form action="quizServlet">
            <input type="text" name="QuizTitle" maxlength="50"/>Add your quiz's title here.
            <br/><input type="text" name="QuizDesc"/>Add your description here.
            <br/><input type="submit" name="action" value="Add quiz">
        </form>
    </body>
</html>
