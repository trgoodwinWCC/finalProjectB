package quizColl;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            System.out.println("Size:"+q.getallQuestions().size());
            for(int i=0;i<(q.getallQuestions().size());i++) {
                pmt = statement.getConnection().prepareStatement(questionSQL,Statement.RETURN_GENERATED_KEYS);
                pmt.setString(1, q.getallQuestions().get(i).getQuestion());
                pmt.setInt(2, q.getallQuestions().get(i).getCorrectAnswerIndex());
                pmt.setInt(3, quizID);
                System.out.println("Position i:"+i);
                pmt.executeUpdate();
                ResultSet rs = pmt.getGeneratedKeys();
                if(rs.next()) {
                    questionID = rs.getInt(1);
                }
                pmt = statement.getConnection().prepareStatement(answerSQL);
                for(int k=0;k<(q.getallQuestions().get(i).getAnswers().size());k++) {
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

    public static String getQuiz(Statement statement, int quizID) {
        // Change this to get just one quiz of quizID
        /* 
        Things to finish: 
        Delete buttons need to be fixed and added for the take-a-quiz page.
        Take a quiz page needs all the quizzes listed.
        ^Make a arraylist of all the quizzes and their id so I can add a delete button foreach match between logged-in user and their quizzes.
        Add/fix the correct answer checkbox and make sure one is picked somehow.
        Maybe do a include on the array display in creating a quiz.
        Maybe make a master css page and inlude it in all the .jsp pages.
        */
        
        /*
        String quizSQL = "SELECT ID,QuizName,QuizDesc,CreatedByUser from Quizzes WHERE ID=?";
        String questionSQL = "SELECT Question Questions values(null, ?, ?, ?)";
        String answerSQL = "insert into Answers values(null, ?, ?)";
        PreparedStatement pmt;
        String error="";
        int quizID = 0;
        int questionID = 0;
        try {
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
            System.out.println("Size:"+q.getallQuestions().size());
            for(int i=0;i<(q.getallQuestions().size());i++) {
                pmt = statement.getConnection().prepareStatement(questionSQL,Statement.RETURN_GENERATED_KEYS);
                pmt.setString(1, q.getallQuestions().get(i).getQuestion());
                pmt.setInt(2, q.getallQuestions().get(i).getCorrectAnswerIndex());
                pmt.setInt(3, quizID);
                System.out.println("Position i:"+i);
                pmt.executeUpdate();
                ResultSet rs = pmt.getGeneratedKeys();
                if(rs.next()) {
                    questionID = rs.getInt(1);
                }
                pmt = statement.getConnection().prepareStatement(answerSQL);
                for(int k=0;k<(q.getallQuestions().get(i).getAnswers().size());k++) {
                    pmt.setString(1, q.getallQuestions().get(i).getAnswers().get(k));
                    pmt.setInt(2, questionID);
                    System.out.println("Postion k:"+k);
                    pmt.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            error = ex.toString();
        }
        System.out.println("got to insert just fine");
        return error;
    */
        return "";
    }
    
    
    //old code from db_people
    public static String getPeople(Statement statement) {
        String error = "";
        try {
            String sql = "select * from PersonCollection";
            System.out.println("sql=" + sql);
            ResultSet rs = statement.executeQuery(sql);
            //people.clear();
            while (rs.next()) {
                String n = rs.getString("Name");
                String e = rs.getString("EyeColor");
                String c = rs.getString("HairColor");
                String h = rs.getString("Height");
                String w = rs.getString("Weight");
                int ind = rs.getInt("PersonSpot");
                //DB_Person bk = new DB_Person(n, e, c, h, w, ind);
                //people.add(bk);
            }
        } catch (SQLException ex) {
            error = ex.toString();
        }
        return error;
    }
}