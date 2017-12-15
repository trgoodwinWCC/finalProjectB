<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Take a quiz</title>
    </head>
    <style>
        .login {
                background-color:lightgrey;
                margin-right: 40px;
                float: right;
            }
    </style>
    <body>
        <form action="index.jsp">
            <input type="submit" value="Go back"/>
        </form>
        <c:if test="${!empty Username}">
            <span class="login">User <span style="font-size:large;color:blue"><c:out value="${Username}"></c:out></span> is logged in. <a href='loginServlet?action=Logout'>Logout</a></span>
        </c:if>
        <c:forEach var="quizzes" items="${AllQuizzes}" varStatus="loopQ">
            <br/><a href="quizServlet?action=TakeQuiz&quizID=<c:out value="${quizzes.quizID}"></c:out>"><c:out value="${quizzes.quizName}"></c:out></a>
            <br/><c:out value="${quizzes.quizDesc}"></c:out>
            <hr/>
        </c:forEach>
    </body>
</html>
