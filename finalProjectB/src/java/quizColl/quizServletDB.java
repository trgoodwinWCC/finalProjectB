package quizColl;
/*
https://stackoverflow.com/tags/servlet-filters/info info about using filters
https://stackoverflow.com/questions/13274279/authentication-filter-and-servlet-for-login the idea is pretty much what I want.
Make sure to change the web.xml for all the changes in code.
*/
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.ConnectionPool;
import jdbc.PasswordSave;

public class quizServletDB extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext servletContext = getServletContext();
        ConnectionPool connectionPool = (ConnectionPool) servletContext.getAttribute("connectionPool");

        HttpSession session = request.getSession();
        Quiz quizBean = (Quiz)session.getAttribute("quiz");
        Quiz saveQuiz = (Quiz)session.getAttribute("SaveQuiz");
        int userInt = (int)session.getAttribute("UserID");
        String usernameLogin = request.getParameter("UsernameLogin");
        String passwordLogin = request.getParameter("PasswordLogin");
        String usernameCreate = request.getParameter("UsernameCreate");
        String passwordCreate = request.getParameter("PasswordCreate");
        
        Connection connection;
        Statement statement;
        String errorMessage = "";
        try {
            connection = connectionPool.getConnection();
            statement = connection.createStatement();

            if (statement != null ) {
                //errorMessage = QuestionCollection.update(statement, request);
                if(saveQuiz!=null)
                    quizSave.insert(statement,quizBean,userInt);
                if(usernameLogin!=null&&passwordLogin!=null) {
                    userInt=PasswordSave.Attempt
                }
                System.out.println("Got to DB");
                statement.close();      
            }
            
            if (connection != null) {
                connectionPool.free(connection);
            }
        } catch (SQLException ex) {
            errorMessage = ex.toString();
        }

        request.setAttribute("errorMessage", errorMessage);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}