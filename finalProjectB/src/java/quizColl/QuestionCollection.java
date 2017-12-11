package quizColl;

import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

// this maybe completely wrong. I am making a servlet for this incase.
public class QuestionCollection {
    
    public static String update(Statement statement, HttpServletRequest request) {
        
        String errorMessage="";
        String action = request.getParameter("action");
        if (action != null ) {
            

            String strIndex;
            int index;

            switch (action) {
                case "Add quiz":
                    String quizTitle = request.getParameter("QuizTitle");
                    String quizDesc = request.getParameter("QuizDesc");
                    //int questionIndex = Integer.parseInt(request.getParameter("questionIndex")); this isnt what I want
                    Quiz quizBean = new Quiz(quizDesc, quizDesc);
                    // session add this?
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/db_personCollection.jsp");
                    dispatcher.forward(request, response);
                    break;
                case "add":
                    errorMessage = book.insert(statement);
                    break;
                case "remove":
                    strIndex = request.getParameter("index");
                    index = Integer.parseInt(strIndex);
                    errorMessage = DB_Book.remove(index, statement);
                    break;
                case "update":
                    strIndex = request.getParameter("index");
                    index = Integer.parseInt(strIndex);
                    errorMessage = book.update(index, statement);
                    break;
            }

        }
        
        ArrayList<Question> bookCollection = new ArrayList<>();
        errorMessage += DB_Book.getBooks(statement, bookCollection);
        request.setAttribute("BookCollection", bookCollection);
        
        return errorMessage;
    }

    private static Object getServletContext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
