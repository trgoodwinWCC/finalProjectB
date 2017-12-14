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
        RequestDispatcher dispatcher = null;
        int userInt = 0;
        Quiz quizBean = (Quiz)session.getAttribute("quiz");
        Quiz saveQuiz = (Quiz)session.getAttribute("SaveQuiz");
        if (session.getAttribute("UserID")!=null)
            userInt = (int)session.getAttribute("UserID");
        String usernameLogin = (String)session.getAttribute("UsernameLogin");
        String passwordLogin = (String)session.getAttribute("PasswordLogin");
        String usernameCreate = (String)session.getAttribute("UsernameCreate");
        String passwordCreate = (String)session.getAttribute("PasswordCreate");
        
        Connection connection;
        Statement statement;
        String errorMessage = "";
        try {
            connection = connectionPool.getConnection();
            statement = connection.createStatement();

            if (statement != null ) {
                //errorMessage = QuestionCollection.update(statement, request);
                if(saveQuiz!=null) {
                    quizSave.insert(statement,saveQuiz,userInt);
                    dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
                }
                if(usernameLogin!=null&&passwordLogin!=null) {
                    userInt=PasswordSave.attemptLogin(usernameLogin, passwordLogin, statement);
                    if(userInt==-1) {
                        errorMessage="Failed to login";
                        request.removeAttribute("UsernameLogin");
                        request.removeAttribute("PasswordLogin");
                        dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                    }
                    else {
                        session.setAttribute("Username", usernameLogin);
                        session.setAttribute("UserID", userInt);
                        dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
                    }
                }
                if(usernameCreate!=null&&passwordCreate!=null) {
                    if(!PasswordSave.createAccount(usernameCreate, passwordCreate, statement))
                        errorMessage="Failed to create account";
                    else
                        errorMessage="Account created, now login";
                    session.removeAttribute("UsernameCreate");
                    session.removeAttribute("PasswordCreate");
                    dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
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
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}