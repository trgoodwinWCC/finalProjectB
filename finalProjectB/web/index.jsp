<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index B</title>
    </head>
    <body>
        <c:out value="${quizMade}"></c:out>
        <a href='createQuiz.jsp'>Create a Quiz</a>
        <a href='QuizIndex'>Take a Quiz</a>
    </body>
</html>