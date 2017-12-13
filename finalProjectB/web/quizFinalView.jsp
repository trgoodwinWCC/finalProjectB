<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
            <%-- consider making a css page for this --%>
        </style>
        <script>
            .getElementById("deleteButton")
            .addEventListener("click", function(){ 
                return confirm("This will delete the quiz, are you sure?");
            });
        </script>
    </head>
    <body>
        <div class="center"><c:out value="${quiz.quizName}"></c:out></div><br/>
        <c:if test="${!empty quiz}">
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
        </c:if>
        <form action="quizServlet" class="center">
            <input type="button" name="action" value="Save quiz" class="center"/>
            <input type="button" name="action" id="deleteButton" value="Abandon quiz" class="center"/>
        </form>
    </body>
</html>
