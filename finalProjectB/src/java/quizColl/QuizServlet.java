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
                    session.setAttribute("quizComplete", false);
                    if((quizTitle!=null&&!quizTitle.isEmpty())&&(quizDesc!=null&&!quizDesc.isEmpty())) {
                        Quiz quizBean = new Quiz(quizTitle, quizDesc);
                        session.setAttribute("quiz", quizBean);
                        dispatcher = getServletContext().getRequestDispatcher("/AddQuestion.jsp");
                    }
                    else {
                        errorMessage="Add the title";
                        session.setAttribute("error", errorMessage);
                        dispatcher = getServletContext().getRequestDispatcher("/createQuiz.jsp");
                    }
                    break;
                case "Add Question":
                    Quiz quizBean = (Quiz)session.getAttribute("quiz");
                    String question1 = req.getParameter("Question");
                    if(question1!=null&&!question1.isEmpty()) {
                        quizBean.setQuestions(question1);
                        session.setAttribute("quiz", quizBean);
                        dispatcher = getServletContext().getRequestDispatcher("/AddAnswer.jsp");
                    }
                    else {
                        errorMessage="Question is empty";
                        session.setAttribute("error", errorMessage);
                        dispatcher = getServletContext().getRequestDispatcher("/AddQuestion.jsp");
                    }
                    break;
                case "Add answer and continue":
                    quizBean = (Quiz)session.getAttribute("quiz");
                    String answer1 = req.getParameter("Answer");
                    if(answer1!=null&&!answer1.isEmpty()) {
                        quizBean.getallQuestions().get(quizBean.getallQuestions().size()-1).setAnswers(answer1);
                        session.setAttribute("quiz", quizBean);
                    }
                    else {
                        errorMessage="Empty answer";
                        session.setAttribute("error", errorMessage);
                    }
                    dispatcher = getServletContext().getRequestDispatcher("/AddAnswer.jsp");
                    break;
                case "Done adding answers":
                    quizBean = (Quiz)session.getAttribute("quiz");
                    String answer2 = req.getParameter("Answer");
                    if(answer2!=null&&!answer2.isEmpty()) {
                        quizBean.getallQuestions().get(quizBean.getallQuestions().size()-1).setAnswers(answer2);
                        session.setAttribute("quiz", quizBean);
                    }
                    dispatcher = getServletContext().getRequestDispatcher("/AddQuestion.jsp");
                    break;
                case "Done adding questions":
                    quizBean = (Quiz)session.getAttribute("quiz");
                    /*
                    String question2 = req.getParameter("Question");
                    if(question2!=null&&!question2.isEmpty()) {
                        quizBean.setQuestions(question2);
                        session.setAttribute("quiz", quizBean);
                    }
                    */
                    quizBean.getallQuestions().removeIf((Question q) -> q.getAnswers().isEmpty());
                    // java 8 made that part easy
                    session.setAttribute("quiz", quizBean);
                    dispatcher = getServletContext().getRequestDispatcher("/quizFinalView.jsp");
                    break;
                case "Set correct answers":
                    quizBean = (Quiz)session.getAttribute("quiz");
                    boolean allHavePair = true;
                    for(int i=0;i<quizBean.getallQuestions().size();i++) {
                        int correctAnswerAtQi;
                        if (req.getParameter(Integer.toString(i))!=null) {
                            correctAnswerAtQi = Integer.parseInt(req.getParameter(Integer.toString(i)));
                            quizBean.getallQuestions().get(i).setCorrectAnswerIndex(correctAnswerAtQi);
                        }
                        else {
                            allHavePair=false;
                        }
                    }
                    if(allHavePair) {
                        session.setAttribute("quiz", quizBean);
                        session.setAttribute("quizComplete", true);
                    }
                    else {
                        errorMessage="A question does not have a correct answer.";
                        session.setAttribute("error", errorMessage);
                    }
                    dispatcher = getServletContext().getRequestDispatcher("/quizFinalView.jsp");
                    break;
                case "Save quiz":
                    quizBean = (Quiz)session.getAttribute("quiz");
                    session.setAttribute("SaveQuiz", quizBean);
                    dispatcher = getServletContext().getRequestDispatcher("/quizServletDB");
                    session.removeAttribute("quiz");
                    session.setAttribute("quizComplete", false);
                    break;
                case "Abandon quiz":
                    session.removeAttribute("quiz");
                    session.setAttribute("quizComplete", false);
                    dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
                    break;
                case "LoadQuizzes":
                    session.setAttribute("AllQuizzes", null);
                    dispatcher = getServletContext().getRequestDispatcher("/quizServletDB");
                    break;
                case "TakeQuiz":
                    int takeQuizID = Integer.parseInt(req.getParameter("quizID"));
                    session.setAttribute("TakeQuizID", takeQuizID);
                    dispatcher = getServletContext().getRequestDispatcher("/quizServletDB");
                    break;
                case "Enter answers":
                    Quiz takeQuiz = (Quiz)session.getAttribute("TakeQuiz");
                    boolean allHaveAnswer = true;
                    double score=0;
                    for(int i=0;i<takeQuiz.getallQuestions().size();i++) {
                        int attempAnswer;
                        if (req.getParameter(Integer.toString(i))!=null) {
                            attempAnswer = Integer.parseInt(req.getParameter(Integer.toString(i)));
                            if(takeQuiz.getallQuestions().get(i).getCorrectAnswerIndex()==attempAnswer)
                                score++;
                        }
                        else {
                            allHaveAnswer=false;
                        }
                    }
                    if(allHaveAnswer) {
                        score=score/takeQuiz.getallQuestions().size();
                        session.setAttribute("PercentageCorrect", score);
                        session.setAttribute("takeQuizComplete", true);
                    }
                    else {
                        errorMessage="Not all questions have a selected answer.";
                        session.setAttribute("error", errorMessage);
                    }
                    dispatcher = getServletContext().getRequestDispatcher("/takeQuiz.jsp");
                    break;
            }
        }
        dispatcher.forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}