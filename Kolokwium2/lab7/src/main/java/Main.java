import database.DatabaseConnection;
import library.UserManager;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Initialize database connection
        DatabaseConnection connection = new DatabaseConnection();
        String dbPath = "src/main/resources/library.db";
        connection.connect(dbPath);

        // Ensure 'users' table exists
        createUsersTableIfNotExists(connection);

        // Test user registration and authentication
        UserManager userManager = new UserManager(connection);

        userManager.register("retom", "1206");
        userManager.authenticate("retom", "1107");
        userManager.authenticate("retom", "1206");

        // Disconnect from the database
        connection.disconnect();
    }

    private static void createUsersTableIfNotExists(DatabaseConnection connection) {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL)";

        try (var conn = connection.getConnection();
             var pstmt = conn.prepareStatement(sql)) {

            pstmt.executeUpdate();
            System.out.println("Table 'users' created or already exists.");

        } catch (SQLException e) {
            System.out.println("Error creating table 'users': " + e.getMessage());
        }
    }
}
