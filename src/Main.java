import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("new string print");

        Class.forName("org.sqlite.JDBC");
        Connection connection;
        String url = "jdbc:sqlite:/db/database.db";
        connection = DriverManager.getConnection(url);

        Statement statement = connection.createStatement();
        boolean result = statement.execute("CREATE TABLE news (x INT, y INT);");
        System.out.println("result = " + result);
    }
}
