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
            pmt = statement.getConnection().prepareStatement(questionSQL,Statement.RETURN_GENERATED_KEYS);
            for(int i=0;i<(q.getallQuestions().size()-1);i++) {
                pmt.setString(1, q.getallQuestions().get(i).getQuestion());
                pmt.setInt(2, q.getallQuestions().get(i).getCorrectAnswerIndex());
                pmt.setInt(3, quizID);
                pmt.executeUpdate();
                ResultSet rs = pmt.getGeneratedKeys();
                if(rs.next()) {
                    questionID = rs.getInt(1);
                }
                pmt = statement.getConnection().prepareStatement(answerSQL);
                for(int k=0;k<(q.getallQuestions().get(i).getAnswers().size()-1);k++) {
                    pmt.setString(1, q.getallQuestions().get(i).getAnswers().get(k));
                    pmt.setInt(2, questionID);
                    pmt.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            error = ex.toString();
        }
        System.out.println("got to insert just fine");
        return error;
    }

    public static void deleteQuiz(int userID, int quizID, Statement statement) {
        String deleteQuizSQL = "delete from Quizzes where CreatedByUser=? AND ID=?";
        PreparedStatement pmt;
        try {
            pmt = statement.getConnection().prepareStatement(deleteQuizSQL);
            pmt.setInt(1, userID);
            pmt.setInt(1, quizID);
            pmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    private static String executeUpdate(String sql, Statement statement) {
        String error = "";
        try {
            System.out.println("sql=" + sql);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            error = e.toString();
        }
        return error;
    }

    public static String getPeople(Statement statement, ArrayList<DB_Person> people) {
        String error = "";
        try {
            String sql = "select * from PersonCollection";
            System.out.println("sql=" + sql);
            ResultSet rs = statement.executeQuery(sql);
            people.clear();
            while (rs.next()) {
                String n = rs.getString("Name");
                String e = rs.getString("EyeColor");
                String c = rs.getString("HairColor");
                String h = rs.getString("Height");
                String w = rs.getString("Weight");
                int ind = rs.getInt("PersonSpot");
                DB_Person bk = new DB_Person(n, e, c, h, w, ind);
                people.add(bk);
            }
        } catch (SQLException ex) {
            error = ex.toString();
        }
        return error;
    }
}