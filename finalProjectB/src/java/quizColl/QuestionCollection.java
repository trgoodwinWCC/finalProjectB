package quizColl;

import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;


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

}
