package quizColl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Quiz {
    
    private String quizName;
    private String quizDesc;
    private ArrayList<Question> allQuestions = new ArrayList<>();

    public Quiz(String quizName, String quizDesc) {
        this.quizName = quizName;
        this.quizDesc = quizDesc;
    }

    public String getQuizName() {
        return quizName;
    }
    public String getQuizDesc() {
        return quizDesc;
    }
    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }
    public void setQuizDesc(String quizDesc) {
        this.quizDesc = quizDesc;
    }
    
    
    public void setallQuestions(String question) {
        Question q = new Question(question);
        if(this.allQuestions.contains(q))
            System.out.println("Already added question");
        else
            this.allQuestions.add(q);
    }
    public ArrayList<Question> getallQuestions() {
        return allQuestions;
    }
    
    
    //other code starts here:
    /*
    
    */
}
