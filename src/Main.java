import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by oleh on 19.11.14.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        MySQLAccess dao = new MySQLAccess();
        dao.readDataBase();

    }
}
