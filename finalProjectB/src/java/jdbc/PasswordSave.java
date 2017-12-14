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

    public static boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Encrypt the clear-text password using the same salt that was used to encrypt the original password
        byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
        // Authentication succeeds if encrypted password that the user entered  is equal to the stored hash.
        return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
    }

    private static byte[] getEncryptedPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
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

    private static byte[] generateSalt() throws NoSuchAlgorithmException {
        // VERY important to use SecureRandom instead of just Random
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        // Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return salt;
    }
    
    public static int attemptLogin(String username, String passwordFromUser, Statement statement) {
        //checks if the user exixts, if so, attempt to validate username and password.
        //returns -1 if anything fails, returns UserID if correct. Note int cannot be null.
        String loginSQL = "select Username from Users where Username=?";
        PreparedStatement pmt;
        boolean loggedIn = false;
        try {
            pmt = statement.getConnection().prepareStatement(loginSQL);
            pmt.setString(1, username);
            ResultSet rs = pmt.executeQuery();
            if (!rs.next()) {
                return -1;
            }
            loginSQL = "select Password,Salt,UserID from Users where Username=?";
            pmt = statement.getConnection().prepareStatement(loginSQL);
            pmt.setString(1, username);
            rs = pmt.executeQuery();
            rs.next();
            byte[] passwordFromDB = rs.getBytes("Password");
            byte[] salt = rs.getBytes("Salt");
            try {
                loggedIn=authenticate(passwordFromUser, passwordFromDB, salt);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                System.out.println("Massive error in PasswordSave");
            }
            if(loggedIn)
                return rs.getInt("UserID");
            else
                return -1;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return -1;
    }
    public static boolean createAccount(String username, String passwordFromUser, Statement statement) {
        String createAccountSQL = "select Username from Users where Username=?";
        PreparedStatement pmt;
        try {
            pmt = statement.getConnection().prepareStatement(createAccountSQL);
            pmt.setString(1, username);
            ResultSet rs = pmt.executeQuery();
            if (rs.next()) {
                System.out.println("Username found, quit");
                return false;
            }
            byte[] salt = generateSalt();
            byte[] passwordToStore = getEncryptedPassword(passwordFromUser, salt);
            createAccountSQL = "insert into Users values(null, ?, ?, ?)";
            pmt = statement.getConnection().prepareStatement(createAccountSQL);
            pmt.setString(1, username);
            pmt.setBytes(2, passwordToStore);
            pmt.setBytes(3, salt);
            pmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            System.out.println("Massive error in PasswordSave");
        }
        return false;
    }
}