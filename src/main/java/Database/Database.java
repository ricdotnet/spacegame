package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private String url = "jdbc:mysql://rrocha.uk/thoughts";
    private String user = "root";
    private String password = "P@ssw0rd";

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
