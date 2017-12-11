package quizColl;

import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;


public class QuestionCollection {
    
    public static String update(Statement statement, HttpServletRequest request) {
        
        String errorMessage="";
        String action = request.getParameter("action");
        if (action != null ) {
            String question = request.getParameter("Question");
            //int questionIndex = Integer.parseInt(request.getParameter("questionIndex"));//this isnt what I want
            String type = request.getParameter("Type");
            Question questionBean = new Question(question, type);

            String strIndex;
            int index;

            switch (action) {
                case "Clear List":
                    errorMessage=DB_Book.remove(-1, statement);
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
