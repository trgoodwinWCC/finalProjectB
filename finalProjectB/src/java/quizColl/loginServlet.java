package quizColl;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class loginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String errorMessage = "";
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = null;
        
        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "Login":
                    String usernameLogin = req.getParameter("UsernameLogin");
                    String passwordLogin = req.getParameter("PasswordLogin");
                    if((usernameLogin!=null&&!usernameLogin.isEmpty())&&(passwordLogin!=null&&!passwordLogin.isEmpty())) {
                        session.setAttribute("UsernameLogin", usernameLogin);
                        session.setAttribute("PasswordLogin", passwordLogin);
                        dispatcher = getServletContext().getRequestDispatcher("/quizServletDB");
                    }
                    else {
                        errorMessage="Empty fields";
                        session.setAttribute("errorMessage", errorMessage);
                        dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                    }
                    break;
                case "Signup":
                    String usernameCreate = req.getParameter("UsernameCreate");
                    String passwordCreate = req.getParameter("PasswordCreate");
                    if((usernameCreate!=null&&!usernameCreate.isEmpty())&&(passwordCreate!=null&&!passwordCreate.isEmpty())) {
                        session.setAttribute("UsernameCreate", usernameCreate);
                        session.setAttribute("PasswordCreate", passwordCreate);
                        dispatcher = getServletContext().getRequestDispatcher("/quizServletDB");
                    }
                    else {
                        errorMessage="Empty fields";
                        session.setAttribute("errorMessage", errorMessage);
                        dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                    }
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