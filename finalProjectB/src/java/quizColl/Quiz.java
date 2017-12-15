package quizColl;

import java.util.ArrayList;

public class Quiz {
    
    private String quizName;
    private String quizDesc;
    private ArrayList<Question> allQuestions = new ArrayList<Question>();
    private int quizID;
    private int createdUserID;

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
    public int getQuizID() {
        return quizID;
    }
    public int getCreatedUserID() {
        return createdUserID;
    }
    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }
    public void setQuizDesc(String quizDesc) {
        this.quizDesc = quizDesc;
    }
    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }
    public void setCreatedUserID(int createdUserID) {
        this.createdUserID = createdUserID;
    }
    
    
    public void setQuestions(String question) {
        Question q = new Question(question);
        if(this.allQuestions.contains(q))
            System.out.println("Already added question");
        else
            this.allQuestions.add(q);
    }
    public ArrayList<Question> getallQuestions() {
        return allQuestions;
    }
    
    
    /* 
    Maybe do a include on the array display in creating a quiz.
    Maybe make a master css page and inlude it in all the .jsp pages.
    */
}