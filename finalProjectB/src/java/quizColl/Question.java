package quizColl;

import java.util.ArrayList;


public class Question {
    
    private String question;
    private int questionIndex;
    private String type;
    private ArrayList<String> answers = new ArrayList<>();
    private int correctAnswerIndex;

    public Question(String question, String type) {
        this.question = question;
        this.type = type;
    }
    
    public String getQuestion() {
        return question;
    }
    public int getQuestionIndex() {
        return questionIndex;
    }
    public String getType() {
        return type;
    }
    public ArrayList<String> getAnswers() {
        return answers;
    }
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
    // end of getters
    public void setQuestion(String question) {
        this.question = question;
    }
    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }
    public void setAnswers(String answer) {
        if (this.answers.contains(answer))
            System.out.println("This answer is already added.");
        else
            this.answers.add(answer);
    }
    
}
