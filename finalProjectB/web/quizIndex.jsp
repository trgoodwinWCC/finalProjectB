<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Take a quiz</title>
    </head>
    <body>
        <form action="index.jsp">
            <input type="submit" value="Go back"/>
        </form>
        <c:forEach var="quizzes" items="${AllQuizzes}" varStatus="loopQ">
            <a href="quizServlet?action=TakeQuiz&quizID=<c:out value="quizzes.quizID"></c:out>"><c:out value="${quizzes.quizName}"></c:out></a>
        </c:forEach>
    </body>
</html>
