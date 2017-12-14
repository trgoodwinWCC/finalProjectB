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
        
    <c:choose>
        <c:when test="${!empty Username}">
            <span class="login">User <span style="font-size:large;color:blue"><c:out value="${Username}"></c:out></span> is logged in</span>
        </c:when>
        <c:otherwise>
            <a class="login" href="login">Login</a>
            <%-- consider "including" the login part into this page 
                Or consider forcing login via java doFilter, and just show that they are logged in.
            --%>
        </c:otherwise>
    </c:choose>
    <body>
        <h2>Quiz creation start</h2>
        <form action="quizServlet">
            <input type="text" name="QuizTitle"/>Add your quiz's title here.
            <br/><input type="text" name="QuizDesc"/>Add your description here.
            <br/><input type="submit" name="action" value="Add quiz">
        </form>
    </body>
</html>
