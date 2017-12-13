package quizColl;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class quizSave {

    public String insert(Statement statement) {
        //change to add in chunks to the db as nomalized data, example: quiz, then questions, then answers
        // First find out if the book is already in the collection:
        String sql2 = "select Name from PersonCollection where Name=? AND EyeColor=? AND HairColor=? AND Height=? AND Weight=?";
        PreparedStatement pmt;
        String error="";
        try {
            pmt = statement.getConnection().prepareStatement(sql2);
            pmt.setString(1, name);
            pmt.setString(2, eyeColor);
            pmt.setString(3, hairColor);
            pmt.setString(4, height);
            pmt.setString(5, weight);
            System.out.println(pmt+" prepared1");
            ResultSet rs = pmt.executeQuery();
            if (rs.next()) {
                return "Person already exists";
            }
            sql2 = "insert into PersonCollection values(?, ?, ?, ?, ?, null)";
            pmt = statement.getConnection().prepareStatement(sql2);
            pmt.setString(1, name);
            pmt.setString(2, eyeColor);
            pmt.setString(3, hairColor);
            pmt.setString(4, height);
            pmt.setString(5, weight);
            pmt.executeUpdate();
        } catch (SQLException ex) {
            error = ex.toString();
        }
        System.out.println("got to insert just fine");
        return error;
    }

    // Note index =-1 will delete all rows
    public static String remove(int index, Statement statement) {
        String sql = "delete from PersonCollection ";
        if (index >= 0) {
            sql += " where PersonSpot=" + index;
        }
        return executeUpdate(sql, statement);
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