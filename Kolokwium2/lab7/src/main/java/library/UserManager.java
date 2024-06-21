package library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import database.DatabaseConnection;

public class UserManager {
    private DatabaseConnection connection;

    public UserManager(DatabaseConnection connection) {
        this.connection = connection;
    }

    public void register(String username, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.executeUpdate();

            System.out.println("User registered successfully.");

        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
    }

    public boolean authenticate(String username, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";
        boolean isAuthenticated = false;

        try (Connection conn = connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                isAuthenticated = BCrypt.checkpw(password, hashedPassword);
            }

        } catch (SQLException e) {
            System.out.println("Error authenticating user: " + e.getMessage());
        }

        return isAuthenticated;
    }
}
