<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Question Page</title>
        <style>
            body {
                background-color: <c:out value="${backgroundColor}">peach</c:out>;
            }
            .login {
                background-color:lightgrey;
                margin-right: 40px;
                float: right;
            }
            <%-- consider making a css page for this --%>
        </style>
    </head>
    <body>
        <c:out value="${quiz.quizName}"></c:out>
        <br/>
        <c:if test="${!empty quiz}">
            <form action="AddQuestion"><%-- this needs to be a servlet --%>
                <c:forEach var="question" items="${quiz.allQuestions}" varStatus="loopQ">
                    <span><c:out value="${question.question}"></c:out></span><input type="button" value="Delete Question"/><input type="hidden" value="${loopQ.index}"/>
                    <c:forEach var="answer" items="${quiz.allQuestions}" varStatus="loopA">
                        <span><c:out value="${answer}"></c:out></span><input type="button" value="Delete Answer"/><input type="hidden" value="${loopA.index}"/>
                    </c:forEach>
                </c:forEach>
            </form>
        </c:if>
        <br/>
        <form action="quizServlet">
        <%-- should I point to a servlet? --%>
            <input type="text" name="Question"/>Add question here.
            <input type="hidden" name="QuestionIndex"/><%-- this one is wrong but I want to do something similar --%>
            <br/><input type="submit" name="action" value="Add Question"/>
            <br/><input type="submit" name="action" value="Done adding questions"/>
        </form>
    </body>
</html>