<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index B</title>
    </head>
    <style>
        .login {
                background-color:lightgrey;
                margin-right: 40px;
                float: right;
            }
    </style>
    <body>
        <c:if test="${!empty Username}">
            <span class="login">User <span style="font-size:large;color:blue"><c:out value="${Username}"></c:out></span> is logged in. <a href='loginServlet?action=Logout'>Logout</a></span>
        </c:if>
        <c:out value="${quizMade}"></c:out>
        <c:set var="takeQuizComplete" value="false"></c:set>
        <a href='createQuiz.jsp'>Create a Quiz</a>
        <a href='quizServlet?action=LoadQuizzes'>Take a Quiz</a>
    </body>
</html>