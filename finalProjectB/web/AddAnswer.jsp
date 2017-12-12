<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add answers</title>
    </head>
    <body>
        <c:out value="${quiz.quizName}"></c:out>
        <br/>
        <c:if test="${!empty quiz}">
            <form action="AddQuestion"><%-- this needs to be a servlet --%>
                <c:forEach var="question" items="${quiz.allQuestions}" varStatus="loopQ">
                    <span><c:out value="${question.question}"></c:out></span><input type="button" value="Delete Question"/><input type="hidden" value="${loopQ.index}"/>
                    <c:forEach var="answer" items="${question.answers}" varStatus="loopA">
                        <span><c:out value="${answer}"></c:out></span><input type="button" value="Delete Answer"/><input type="hidden" value="${loopA.index}"/>
                    </c:forEach>
                </c:forEach>
            </form>
            <br/>
        </c:if>
        
        <form action="quizServlet">
        <%-- should I point to a servlet? --%>
            <input type="text" name="Answer"/>Add answer here.
            <br/><input type="checkbox" name="AnswerCorrect"/>Is the the correct answer?
            <br/><input type="submit" name="action" value="Add answer and continue"/>
            <br/><input type="submit" name="action" value="Done adding answers"/>
        </form>
    </body>
</html>