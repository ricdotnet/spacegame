package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private String url = "jdbc:mysql://localhost/dbname";
    private String user = "root";
    private String password = "";

    private Connection connection;

    public Database() {
        setConnection();
    }

    private void setConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            return;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        connection = null;
    }

    public Connection getConnection() {
        return connection;
    }

}
