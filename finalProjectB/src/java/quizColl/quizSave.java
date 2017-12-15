package quizColl;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class quizSave {

    public static String insert(Statement statement, Quiz q, int userID) {
        // This is nomalized data,which each insert follows the other. example: quiz, then all questions, then all answers
        String quizSQL = "insert into Quizzes values(null, ?, ?, ?)";
        String questionSQL = "insert into Questions values(null, ?, ?, ?)";
        String answerSQL = "insert into Answers values(null, ?, ?)";
        PreparedStatement pmt;
        String error="";
        int quizID = 0;
        int questionID = 0;
        try {
            //quiz part
            pmt = statement.getConnection().prepareStatement(quizSQL,Statement.RETURN_GENERATED_KEYS);
            pmt.setString(1, q.getQuizName());
            pmt.setString(2, q.getQuizDesc());
            pmt.setInt(3, userID);
            pmt.executeUpdate();
            ResultSet rs = pmt.getGeneratedKeys();
            if(rs.next()) {
                quizID = rs.getInt(1);
            }
        } catch (SQLException ex) {
            error = ex.toString();
        }

        try {
            for(int i=0;i<(q.getallQuestions().size());i++) {
                //question part
                pmt = statement.getConnection().prepareStatement(questionSQL,Statement.RETURN_GENERATED_KEYS);
                pmt.setString(1, q.getallQuestions().get(i).getQuestion());
                pmt.setInt(2, q.getallQuestions().get(i).getCorrectAnswerIndex());
                pmt.setInt(3, quizID);
                pmt.executeUpdate();
                ResultSet rs = pmt.getGeneratedKeys();
                if(rs.next()) {
                    questionID = rs.getInt(1);
                }
                pmt = statement.getConnection().prepareStatement(answerSQL);
                for(int k=0;k<(q.getallQuestions().get(i).getAnswers().size());k++) {
                    //answers part
                    pmt.setString(1, q.getallQuestions().get(i).getAnswers().get(k));
                    pmt.setInt(2, questionID);
                    System.out.println("Postion k:"+k);
                    pmt.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            error = ex.toString();
        }
        return error;
    }

    public static void deleteQuiz(int userID, int quizID, Statement statement) {
        String deleteQuizSQL = "delete from Quizzes where CreatedByUser=? AND ID=?";
        PreparedStatement pmt;
        try {
            pmt = statement.getConnection().prepareStatement(deleteQuizSQL);
            pmt.setInt(1, userID);
            pmt.setInt(2, quizID);
            pmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    //change void to ArrayList<Quiz> MAKE SURE TO CHECK TOMCAT FOR BUG THAT NEEDS THE REDUNDANT generic: ArrayList<Quiz> temp = new ArrayList<Quiz>();
    public static ArrayList<Quiz> getAllQuizzes(Statement stmt) {
        String quizSQL = "Select ID,QuizName,QuizDesc,CreatedByUser FROM Quizzes";
        ArrayList<Quiz> allQuizzes = new ArrayList<Quiz>();
        PreparedStatement pmt;
        String error="";
        try {
            pmt = stmt.getConnection().prepareStatement(quizSQL);
            ResultSet rs = pmt.executeQuery(quizSQL);
            while (rs.next()) {
                int quizID = rs.getInt("ID");
                String quizName = rs.getString("QuizName");
                String quizDesc = rs.getString("QuizDesc");
                int createdByUser = rs.getInt("CreatedByUser");
                Quiz temp = new Quiz(quizName, quizDesc);
                temp.setQuizID(quizID);
                temp.setCreatedUserID(createdByUser);
                allQuizzes.add(temp);
            }
        } catch (SQLException ex) {
            System.out.println("Error:"+ex.toString());
        }
        return allQuizzes;
    }
    public static Quiz getQuiz(Statement statement, int quizID) {
        
        String quizSQL = "SELECT QuizName,QuizDesc,CreatedByUser from Quizzes WHERE ID=?";
        String questionSQL = "SELECT QuestionID,Question,CorrectAnswerIndex from Questions WHERE QuizID=?";
        String answerSQL = "SELECT Answer from Answers WHERE QuestionID=?";
        PreparedStatement pmt;
        String error="";
        int questionID = 0;
        String quizName;
        String quizDesc;
        int createdByUser;
        try {
            pmt = statement.getConnection().prepareStatement(quizSQL);
            pmt.setInt(1, quizID);
            ResultSet rs = pmt.executeQuery();
            if(rs.next()) {
                quizName = rs.getString("QuizName");
                quizDesc = rs.getString("QuizDesc");
                createdByUser = rs.getInt("CreatedByUser");
            }
            else {
                System.out.println("Quiz#"+quizID+" not found");
                return null;
            }
            Quiz wantedQuiz = new Quiz(quizName, quizDesc);
            wantedQuiz.setQuizID(quizID);
            wantedQuiz.setCreatedUserID(createdByUser);
            //question
            pmt = statement.getConnection().prepareStatement(questionSQL);
            pmt.setInt(1, quizID);
            rs = pmt.executeQuery();
            int i=0;
            while(rs.next()) {
                questionID = rs.getInt("QuestionID");
                String question = rs.getString("Question");
                int correctAnswerIndex = rs.getInt("CorrectAnswerIndex");
                wantedQuiz.getallQuestions().add(new Question(question));
                wantedQuiz.getallQuestions().get(i).setCorrectAnswerIndex(correctAnswerIndex);
                wantedQuiz.getallQuestions().get(i).setQuestionIndex(questionID);
                i++;
            }
            //
            //pmt = statement.getConnection().prepareStatement(answerSQL);
            for(int k=0;k<(wantedQuiz.getallQuestions().size());k++) {
                pmt = statement.getConnection().prepareStatement(answerSQL);
                pmt.setInt(1, wantedQuiz.getallQuestions().get(k).getQuestionIndex());
                rs = pmt.executeQuery();
                while(rs.next()) {
                    String answer = rs.getString("Answer");
                    wantedQuiz.getallQuestions().get(k).setAnswers(answer);
                }
            }
            return wantedQuiz;
        } catch (SQLException ex) {
            error = ex.toString();
        }
        return null;
    }
}