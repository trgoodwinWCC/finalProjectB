package jdbc;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordSave {

    public boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Encrypt the clear-text password using the same salt that was used to encrypt the original password
        byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
        // Authentication succeeds if encrypted password that the user entered  is equal to the stored hash.
        return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
    }

    public byte[] getEncryptedPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // PBKDF2 with SHA-1 as the hashing algorithm. Note that the NIST
        // specifically names SHA-1 as an acceptable hashing algorithm for PBKDF2
        String algorithm = "PBKDF2WithHmacSHA1";
        // SHA-1 generates 160 bit hashes, so that's what makes sense here
        int derivedKeyLength = 160;
        // Pick an iteration count that works for you. The NIST recommends at
        // least 1,000 iterations:
        // http://csrc.nist.gov/publications/nistpubs/800-132/nist-sp800-132.pdf
        // iOS 4.x reportedly uses 10,000:
        // http://blog.crackpassword.com/2010/09/smartphone-forensics-cracking-blackberry-backup-passwords/
        int iterations = 20000;
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
        SecretKeyFactory f= SecretKeyFactory.getInstance(algorithm);
        return f.generateSecret(spec).getEncoded();
    }

    public byte[] generateSalt() throws NoSuchAlgorithmException {
        // VERY important to use SecureRandom instead of just Random
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        // Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return salt;
    }
    public static void main(String[] args) {
        // I dont really understand on how to store the password or if its really working/check to make sure that the hash is not using sha-1
        String password = "AtestPassword";
        PasswordSave passwordEn = new PasswordSave();
        byte[] salt = null;
        byte[] pass = null;
        try {
            salt = passwordEn.generateSalt();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PasswordSave.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pass = passwordEn.getEncryptedPassword(password, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(PasswordSave.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* so it is working, use something like following and mysql blob type to send/retrive to db
        String sql = "INSERT INTO mysql_all_table (col_binarystream) VALUES(?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        //java2s.com
        byte[] buffer = "some data".getBytes();
        pstmt.setBytes(1, buffer);
        pstmt.executeUpdate();
        pstmt.close();
        
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM mysql_all_table");
        while (resultSet.next()) {
          byte[] bytes = resultSet.getBytes("col_binarystream");
        }
        */
        for(byte bit: pass) {
            System.out.println(bit);
        }
        System.out.println("---");
        String encrypt = null;
        try {
            encrypt = new String(pass,"UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PasswordSave.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(encrypt);
    }
    public boolean attemptLogin(String username, String passwordFromUser, Statement statement) {
        //checks if the user exixts, if so, attempt to validate username and password.
        //returns false if anything fails, returns true if correct
        String sql2 = "select User from accountTable where User=?";
        PreparedStatement pmt;
        try {
            pmt = statement.getConnection().prepareStatement(sql2);
            pmt.setString(1, username);
            ResultSet rs = pmt.executeQuery();
            if (!rs.first()) {
                return false;
            }
            sql2 = "select Password,Salt from accountTable where User=?";
            pmt = statement.getConnection().prepareStatement(sql2);
            pmt.setString(1, username);
            rs = pmt.executeQuery();
            rs.next();
            byte[] passwordFromDB = rs.getBytes("Password");
            byte[] salt = rs.getBytes("Salt");
            try {
                return authenticate(username, passwordFromDB, salt);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(PasswordSave.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return false;
    }
    public boolean createAccount(String username, String passwordFromUser, Statement statement) {
        String sql2 = "select User from accountTable where User=?";
        PreparedStatement pmt;
        try {
            pmt = statement.getConnection().prepareStatement(sql2);
            pmt.setString(1, username);
            ResultSet rs = pmt.executeQuery();
            if (!rs.first()) {
                return false;
            }
            byte[] salt = generateSalt();
            byte[] passwordToStore = getEncryptedPassword(username, salt);
            sql2 = "insert into accountTable values(?, ?, ?)";
            pmt = statement.getConnection().prepareStatement(sql2);
            pmt.setString(1, username);
            pmt.setBytes(2, passwordToStore);
            pmt.setBytes(3, salt);
            pmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(PasswordSave.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}