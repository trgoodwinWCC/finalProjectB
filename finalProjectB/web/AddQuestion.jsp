<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
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
            table {
                border-spacing: 2px;
                border-color: gray;
                border-style: solid;
            }
            th, td {
                border: solid 2px lightgrey;
                padding: 10px;
            }
            <%-- consider making a css page for this --%>
        </style>
    </head>
    <body>
        <c:if test="${!empty Username}">
            <span class="login">User <span style="font-size:large;color:blue"><c:out value="${Username}"></c:out></span> is logged in. <a href='loginServlet?action=Logout'>Logout</a></span>
        </c:if>
        <c:out value="${quiz.quizName}"></c:out><br/>
        <form action="quizServlet">
            <input type="text" name="Question"/>Add question here.
            <br/><input type="submit" name="action" value="Add Question"/>
            <br/><input type="submit" name="action" value="Done adding questions"/>
        </form>
        <br/>
        <c:if test="${!empty quiz}">
            <form action="quizServlet">
                <c:forEach var="question" items="${quiz.allQuestions}" varStatus="loopQ">
                    <table>
                        <tr>
                            <th colspan="<c:out value="${fn:length(question.answers)}"></c:out>"><c:out value="Question: ${question.question}"></c:out></th>
                        </tr>
                        <tr>
                            <c:forEach var="answer" items="${question.answers}" varStatus="loopA">
                                <td>
                                    <c:out value="${answer}"></c:out>
                                </td>
                            </c:forEach>
                        </tr>
                    </table>
                </c:forEach>
            </form>
        </c:if>
    </body>
</html>