package database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBServices {
    public static Statement statement;

    public DBServices() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            statement = DriverManager.getConnection("jdbc:mysql://localhost:3306/voting", "root", "").createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
