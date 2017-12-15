<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Take a this quiz</title>
        <style>
            body {
                background-color: <c:out value="${backgroundColor}">peach</c:out>;
            }
            .login {
                background-color:lightgrey;
                margin-right: 40px;
                float: right;
            }
            table {
                border-spacing: 2px;
                border-color: gray;
                border-style: solid;
                margin-left:auto;
                margin-right:auto;
            }
            th, td {
                border: solid 2px lightgrey;
                padding: 10px;
            }
            .center {
                text-align: center;
            }
        </style>
    </head>
    <body>
        <c:if test="${!empty Username}">
            <span class="login">User <span style="font-size:large;color:blue"><c:out value="${Username}"></c:out></span> is logged in. <a href='loginServlet?action=Logout'>Logout</a></span>
        </c:if>
        <br/><div class="center"><c:out value="${TakeQuiz.quizName}"></c:out></div><br/>
        <c:if test="${!empty TakeQuiz}">
            <c:if test="${!takeQuizComplete}">
                <form action="quizServlet">
                    <c:forEach var="question" items="${TakeQuiz.allQuestions}" varStatus="loopQ">
                        <table>
                            <tr>
                                <th colspan="<c:out value="${fn:length(question.answers)}"></c:out>"><c:out value="Question: ${question.question}"></c:out></th>
                            </tr>
                            <tr>
                                <c:forEach var="answer" items="${question.answers}" varStatus="loopA">
                                    <td>
                                        <c:out value="${answer}"></c:out><c:if test="${!quizComplete}"><input type="radio" name="${loopQ.index}" value="${loopA.index}"></c:if>
                                    </td>
                                </c:forEach>
                            </tr>
                        </table>
                    </c:forEach>
                    <input type="submit" name="action" value="Enter answers"/>
                </form>
            </c:if>
        </c:if>
        <c:if test="${takeQuizComplete}">
            <br/>
            <c:out value="${score}"></c:out>
            <form action="index.jsp" class="center">
                <input type="submit" name="action" value="Go back to index" class="center"/>
            </form>
        </c:if>
    </body>
</html>
