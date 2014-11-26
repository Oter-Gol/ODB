package access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

    private String config;
    private String driver;
    private Connection connection;

    public Connector() {
        String url = "jdbc:mysql://host-176-36-112-223.la.net.ua/";
        String db = "ODB_Lab4";
        String login = "oter";
        String password = "12345";
        this.driver = "com.mysql.jdbc.Driver";
        this.config = url + db + "?user=" + login + "&password=" + password;

        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(config);
        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
