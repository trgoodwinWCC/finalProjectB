package quizColl;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class QuizServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        ServletContext servletContext = getServletContext();
        String errorMessage = "";
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher;
        
        String action = req.getParameter("action");
        if (action != null ) {
            

            String strIndex;
            int index;

            switch (action) {
                case "Add quiz":
                    String quizTitle = req.getParameter("QuizTitle");
                    String quizDesc = req.getParameter("QuizDesc");
                    Quiz quizBean = new Quiz(quizTitle, quizDesc);
                    // session add this like so?
                    session.setAttribute("quiz", quizBean);
                    dispatcher = getServletContext().getRequestDispatcher("/AddQuestion.jsp");
                    break;
                case "Add Question":
                    quizBean = (Quiz)session.getAttribute("quiz");
                    String question = req.getParameter("Question");
                    quizBean.setallQuestions(question);
                    dispatcher = getServletContext().getRequestDispatcher("/AddAnswer.jsp");
                    break;
                case "remove":
                    strIndex = req.getParameter("index");
                    index = Integer.parseInt(strIndex);
                    errorMessage = DB_Book.remove(index, statement);
                    break;
                case "update":
                    strIndex = req.getParameter("index");
                    index = Integer.parseInt(strIndex);
                    errorMessage = book.update(index, statement);
                    break;
            }

        }
        
        //dispatcher = getServletContext().getRequestDispatcher("/db_personCollection.jsp");
        dispatcher.forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
