package quizColl;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class QuizServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String errorMessage = "";
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = null;
        
        String action = req.getParameter("action");
        if (action != null) {
            
            switch (action) {
                case "Add quiz":
                    String quizTitle = req.getParameter("QuizTitle");
                    String quizDesc = req.getParameter("QuizDesc");
                    Quiz quizBean = new Quiz(quizTitle, quizDesc);
                    session.setAttribute("quiz", quizBean);
                    dispatcher = getServletContext().getRequestDispatcher("/AddQuestion.jsp");
                    break;
                case "Add Question":
                    quizBean = (Quiz)session.getAttribute("quiz");
                    String question1 = req.getParameter("Question");
                    quizBean.setallQuestions(question1);
                    session.setAttribute("quiz", quizBean);
                    dispatcher = getServletContext().getRequestDispatcher("/AddAnswer.jsp");
                    break;
                case "Add answer and continue":
                    quizBean = (Quiz)session.getAttribute("quiz");
                    String answer1 = req.getParameter("Answer");
                    System.out.println(req.getParameter("AnswerCorrect"));
                    quizBean.getallQuestions().get(quizBean.getallQuestions().size()-1).setAnswers(answer1);
                    session.setAttribute("quiz", quizBean);
                    dispatcher = getServletContext().getRequestDispatcher("/AddAnswer.jsp");
                    break;
                case "Done adding answers":
                    quizBean = (Quiz)session.getAttribute("quiz");
                    String answer2 = req.getParameter("Answer");
                    quizBean.getallQuestions().get(quizBean.getallQuestions().size()-1).setAnswers(answer2);
                    session.setAttribute("quiz", quizBean);
                    dispatcher = getServletContext().getRequestDispatcher("/AddQuestion.jsp");
                    break;
                case "Done adding questions":
                    quizBean = (Quiz)session.getAttribute("quiz");
                    String question2 = req.getParameter("Question");
                    quizBean.setallQuestions(question2);
                    session.setAttribute("quiz", quizBean);
                    dispatcher = getServletContext().getRequestDispatcher("/Review.jsp");
                    break;
                case "Delete Question":
                    //this part unfinised.
                    quizBean = (Quiz)session.getAttribute("quiz");
                    int questionIndex = Integer.parseInt(req.getParameter("questionIndex"));
                    quizBean.getallQuestions().remove(questionIndex);
                    session.setAttribute("quiz", quizBean);
                    System.out.println("--Got to delete--"+questionIndex+"--"+req.getHeader("Referer"));
                    //dispatcher = getServletContext().getRequestDispatcher(req.getHeader("Referer"));
                    resp.sendRedirect(req.getHeader("Referer"));
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